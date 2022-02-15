var createbtn = document.querySelector("create");
let id = window.localStorage.getItem('courseId')


function addAssignment() {
    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/assignments";
    var bearer = "Bearer " + token;

    const data = {

        "assignmentName": document.getElementById("cname").value,
        "dueDate": document.getElementById("dueDate").value,
        "fileFormat": document.getElementById("fileFormat").value,
        "fileQuantity": document.getElementById("numberFile").value,
        "feedbackContent": "",
        "courseId": id

    };
    console.log(data);
    const other_params = {
        headers: {
            Authorization: bearer,
            "X-FP-API-KEY": "token1",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        method: "POST",
    };

    fetch(url, other_params)
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {
                return response.json().then(error => { throw new Error(error.message) });
            }
        })
        .then(function(data) {
            alert("Created assignment successfullly !")
            window.location = "../course/courseDetail.html?" + id
        })
        .catch(function(error) {
            alert(error)
        });
    return true;
}