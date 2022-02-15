var url_default = window.location.search;
id = url_default.replace("?", '');
document.getElementById('submitBtn').href = "uploadFile.html?" + id;

function getAsmById() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/assignments/" + id;
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
            if (data.fileName === '' || data.fileName === null) {
                document.getElementById("submitButton").innerText = "Add Submission";
            } else {
                document.getElementById("submitButton").innerText = "Edit Submission";
            }
            if (data.gradeStatus === 0) {
                document.getElementById('asmGrade').style.display = 'none';
            }
            let gradeStatus = data.gradeStatus === 0 ? "Not Grade" : "Graded";
            console.log(data);
            const due_date = data.dueDate.split("T")[0];

            document.getElementById('asmName').innerHTML = data.assignmentName;
            document.getElementById('grading').innerHTML = gradeStatus;
            var d = new Date(data.dueDate);
            document.getElementById('due_date').innerHTML = due_date + ' ' + d.getUTCHours() + 'h ' + d.getUTCMinutes() + 'm ' + d.getUTCSeconds() + 's';
            document.getElementById('timeRemaining').innerHTML = data.timeRemaining;
            document.getElementById('submitButton').href = "uploadFile.html?" + id;

            document.getElementById('fileupload').innerHTML = data.fileName;
            const submitdate = data.submitDate.split("T")[0];
            document.getElementById('lastModified').innerHTML = submitdate + ' ' + d.getUTCHours() + 'h ' + d.getUTCMinutes() + 'm ' + d.getUTCSeconds() + 's';
            document.getElementById('grade').innerHTML = data.grade;
            document.getElementById('comment').innerHTML = data.comment;
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getAsmById()