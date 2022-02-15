var url_default = window.location.search;
id = url_default.replace("?", ''); // remove the ?

token = window.localStorage.getItem("access_token");
const teachersAPI = "http://localhost:8080/users/role/teacher";
const topicAPI = "http://localhost:8080/topics/" + id;
var cname = document.getElementById("cname");
var desc = document.getElementById("desc");
var bearer = "Bearer " + token;


const remainTeacherTable = document.getElementById("remainTeachers");
const currentTeacherTable = document.getElementById("currentTeachers");

let allTeachers = []
let remainTeachers = [];
let currentTeachers = []
let topicDetail = {};

let currentTeacherIds = []


const header = {
    method: "GET",
    headers: {
        'Authorization': bearer,
        'X-FP-API-KEY': 'token', //it can be iPhone or your any other attribute
        'Content-Type': 'application/json'
    }
}


const request = async() => {
    const teacherResponse = await fetch(teachersAPI, header);
    const teacherJson = await teacherResponse.json();

    const topicResponse = await fetch(topicAPI, header);
    const topicJson = await topicResponse.json();

    return [teacherJson, topicJson]
}


const renderRemainTeachers = (remainTeachers) => {
    remainTeacherTable.innerHTML = remainTeachers.map(teacher =>
        `<tr onClick='addToCurrent(${teacher.id})' id=${teacher.id}>
          <td>${teacher.id}</td>
          <td>${teacher.firstName} ${teacher.lastName}</td>
          <td>${teacher.email}</td>
        </tr>`
    ).join('')
}

const renderCurrentTeachers = (currentTeachers) => {
    currentTeacherTable.innerHTML = currentTeachers.map(teacher =>
        `<tr id=${teacher.id}>
          <td>${teacher.id}</td>
          <td>${teacher.firstName} ${teacher.lastName}</td>
          <td>${teacher.email}</td>
          <td onclick='removeCurrent(${teacher.id})'><button style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close">
          <span aria-hidden="true">&times;</span></button></td>
        </tr>`
    ).join('')
}


const addToCurrent = (id) => {
    currentTeacherIds.push(id);

    remainTeachers = remainTeachers.filter(teacher => teacher.id !== id);

    allTeachers.forEach(teacher => {
        if (teacher.id === id) currentTeachers.push(teacher);
    });

    renderRemainTeachers(remainTeachers);
    renderCurrentTeachers(currentTeachers);
}

const removeCurrent = (id) => {
    currentTeacherIds.pop(id);

    currentTeachers = currentTeachers.filter(teacher => teacher.id !== id);

    allTeachers.forEach(teacher => {
        if (teacher.id === id) remainTeachers.push(teacher);
    });

    renderRemainTeachers(remainTeachers);
    renderCurrentTeachers(currentTeachers);
}

request().then(([teachers, topicDetail]) => {
    allTeachers = teachers;

    currentTeacherIds = topicDetail.teacherIds;

    teachers.forEach(teacher => {
        if (currentTeacherIds.includes(teacher.id)) {
            currentTeachers.push(teacher);
        } else {
            remainTeachers.push(teacher);
        }
    });

    cname.value = topicDetail.name;
    desc.value = topicDetail.description;

    renderRemainTeachers(remainTeachers);
    renderCurrentTeachers(currentTeachers);
})


const editTopic = () => {

    const data = {
        "description": desc.value,
        "teacherIds": currentTeacherIds

    };

    const postHeader = {
        headers: {
            Authorization: bearer,
            "X-FP-API-KEY": "token1",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        method: "PUT",
    };

    fetch(topicAPI, postHeader)
        .then(res => {
            if (res.status === 404) {
                alert("Teacher can not be empty")
            } else {
                alert("Topic edited successfully !")
                window.location.assign("../topic/listTopic.html")
            }
        })
        .catch(err => alert(err.message))
    return true;
}