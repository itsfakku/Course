// function getToken() {
//     postman = require('postman-collection').Collection,
//     myCollection;
//     // document.getElementById('message').className = "text-success";
//     // document.getElementById('message').innerHTML = "checking";

//     const url = "http://localhost:8080/authenticate";
//     const data = {
//         "username": "admin",
//         "password": "admin",
//     };
//     const other_params = {
//         headers: { "content-type": "application/json; charset=UTF-8" },
//         body: JSON.stringify(data),
//         method: "POST",
//         mode: "cors"
//     };

//     fetch(url, other_params)
//         .then(function (response) {
//             if (response.ok) {
//                 return response.json();
//             } else {
//                 throw new Error("Could not reach the API: " + response.statusText);
//             }
//         }).then(function (data) {
//             console.log(data)
//             var jsonData = JSON.parse(responseBody);
//             var token = jsonData.jwtToken;
//             postman.setEnvironmentVariable("token", token);
//         }).catch(function (error) {

//         });
//     return true;
// }
// getToken()
function getLastCourse() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/courses/";
    var bearer = 'Bearer ' + token;
    fetch(url, {
            method: "GET",
            headers: {
                'Authorization': bearer,
                'X-FP-API-KEY': 'token', //it can be iPhone or your any other attribute
                'Content-Type': 'application/json'
            }
        })
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        }).then(data => {
            document.querySelector("#lastestcourse").innerHTML = '';
            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    const createdDate = item.createdDate.split("T")[0];
                    content += `
                    <tr>
                    <td>
                        <a value="${item.id}" style="text-decoration: none;" href="course/courseDetail.html?${item.id}">${item.id}</a>
                    </td>
                    <td>${item.name}</td>
                    <td>${createdDate}</td>
                    <td>${item.topicName}</td>
                    
                </tr>
            `
                })
                document.querySelector("#lastestcourse").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getLastCourse()