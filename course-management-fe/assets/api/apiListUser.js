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
function getUser() {
    token = window.localStorage.getItem('access_token')

    const currentLoggedUser = window.localStorage.getItem('username');

    const url = "http://localhost:8080/users";
    var bearer = 'Bearer ' + token;
    fetch(url, {
            method: "get",
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
            document.querySelector("#listuser").innerHTML = '';
            if (data.length > 0) {
                var content = ``;
                data.filter(item => item.username !== currentLoggedUser).map((item, index) => {

                    let status = item.status === 1 ? "Active" : "New";
                    content += `
            <tr>
                <td>${++index}</td>
                <td>${item.role}</td>
                <td>${item.username}</td>
                <td>${item.email}</td>
                <td class="hidden-480">${item.firstName} ${item.lastName}</td>
                <td>${item.phone}</td>
                <td>${item.address}</td>
                <td>${status}</td>

                <td>
                    <div class="hidden-sm hidden-xs action-buttons">
                        <a class="blue" href="proFile.html?${item.id}" onclick="getProfileById()">
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                        </a>

                        <a id="btnEditUser" class="green" href="editUser.html?${item.id}">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                        </a>

                        <a id="btnDisableUser" class="red" href="#" onclick="disableUser(${item.id})">
                            <i class="ace-icon fa fa-remove bigger-130"></i>
                        </a>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle"
                                data-toggle="dropdown" data-position="auto">
                                <i
                                    class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul
                                class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip"
                                        title="View">
                                        <span class="blue">
                                            <i
                                                class="ace-icon fa fa-search-plus bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a  onclick="editUser(${item.id})" class="tooltip-success"
                                        data-rel="tooltip" title="Edit">
                                        <span class="green">
                                            <i
                                                class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a class="red" href="#" onclick="disableUser(${item.id})">
                                        <span class="red">
                                            <i
                                                class="ace-icon fa fa-remove bigger-120"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            `
                })
                document.querySelector("#listuser").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getUser()