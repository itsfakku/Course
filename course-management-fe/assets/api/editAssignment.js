var url_default = window.location.search;
id = url_default.replace("?", ''); // remove the ?
let ids = window.localStorage.getItem('courseId')

token = window.localStorage.getItem("access_token");
const url = "http://localhost:8080/assignments?id=" + id;
const urls = "http://localhost:8080/assignments/" + id;

var bearer = "Bearer " + token;
getAssignment = (id) => {
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
        })
        .then(data => {
            const dueDate = data.dueDate.split("T")[0];
            cname.value = data.assignmentName;
            numberFile.value = data.fileQuantity;
            DueDate.value = dueDate;
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getAssignment(id)



editAssignment = (id) => {
    const data = {

        "assignmentName": document.getElementById("cname").value,
        "dueDate": document.getElementById("DueDate").value,
        "fileFormat": document.getElementById("fileFormat").value,
        "fileQuantity": document.getElementById("numberFile").value,
        "feedbackContent": "",
        "courseId": ids
    };
    const other_params = {
        headers: {
            Authorization: bearer,
            "X-FP-API-KEY": "token1",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        method: "PATCH",
    };
    fetch(urls, other_params)
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {

                return response.json().then(error => {throw new Error(error.message)});
            }
        })
        .then(function(data) {
            window.location.assign("../course/courseDetail.html?" +ids)

            alert("Edited assignment !")
        })
        .catch(function(error) {
            alert(error)
        });
    return true;
}