var createbtn = document.querySelector("submit");

var url_default = window.location.search;
asmId = url_default.replace("?", '');

const submit = (file) => {


    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Bearer " + token);


    var formdata = new FormData();
    formdata.append("file", file);
    console.log(file)

    var requestOptions = {
        method: 'PATCH',
        headers: myHeaders,
        body: formdata,
        redirect: 'follow'
    };

    fetch("http://localhost:8080/assignments/" + asmId + "/submit", requestOptions)
        .then(response => response.text())
        .then(result => {
            const submitTime = JSON.parse(result).submitDate;

            alert("Submit done at: " + submitTime)
            window.location.replace("../course/assignMent.html?" + asmId)
        })
        .catch(error => console.log('error', error));
}
var fileInput = document.getElementById("fileDemo");
const upload = () => submit(fileInput.files[0])
const fileE = document.getElementById("submit");

fileE.addEventListener("click", upload)