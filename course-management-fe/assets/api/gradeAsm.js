var url_default = window.location.search;
stuId = url_default.replace("?", ''); // remove the ?

let asmId = window.localStorage.getItem('asmId')

var grade = document.getElementById("grade");
var desc = document.getElementById("desc");
var fileUrl = document.getElementById("src");
var studentId = document.getElementById("studentID");
var studentName = document.getElementById("studentName");

token = window.localStorage.getItem("access_token");
const apiGetAsm = "http://localhost:8080/assignments/" + asmId + "/student/" + stuId;
const gradeAsmAPI = "http://localhost:8080/submissions/" + asmId + "/grade/" + stuId;

var bearer = "Bearer " + token;

function getAsm() {
    fetch(apiGetAsm, {
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
            studentId.innerHTML = "Student Id :" + data.studentId;
            studentName.innerHTML = "Student Name : " + data.studentName;
            fileUrl.src = "http://localhost:8080/files/" + data.fileUrl;
            console.log(data)
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getAsm()

function gradeAsm() {
    const data = {
        "grade": document.getElementById("grade").value,
        "comment": document.getElementById("desc").value,
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
    fetch(gradeAsmAPI, other_params)
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {
                return response.json().then(error => { throw new Error(error.message) });
            }
        })
        .then(function(data) {
            window.location.assign("../course/listGrade.html?" + asmId)
            alert("Graded assignment !")
        })
        .catch(function(error) {
            alert(error)
        });
    return true;
}

function Cancel() {
    window.location.assign("../course/listGrade.html?" + asmId)
}