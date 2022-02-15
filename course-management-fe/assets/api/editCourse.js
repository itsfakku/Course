var url_default = window.location.search;
var studentTable = document.getElementById("addStudent");
id = url_default.replace("?", ""); // remove the ?

token = window.localStorage.getItem("access_token");
const url = "http://localhost:8080/courses/" + id;
var bearer = "Bearer " + token;

var name = document.getElementById("cname");
var desc = document.getElementById("desc");
var enrollCode = document.getElementById("code");
var date = document.getElementById("date");
var currentTopic = document.getElementById("topic");

const header = {
    method: "GET",
    headers: {
        Authorization: bearer,
        "X-FP-API-KEY": "token", //it can be iPhone or your any other attribute
        "Content-Type": "application/json",
    },
};

var startDate;
var endDate;

var teachearId;

token = window.localStorage.getItem("access_token");

var bearer = "Bearer " + token;

const remainTeacherTable = document.getElementById("remainTeachers");
const currentTeacherTable = document.getElementById("currentTeacher");

const remainStudentTable = document.getElementById("addStudent");
const currentStudentTable = document.getElementById("bodyStudent");

const studentAPI = "http://localhost:8080/users/role/student";
let allStudents = [];
let remainStudents = [];
let currentStudents = [];
let allStudentId = [];
let currentStudentIds;


let allTeachers = [];
let remainTeachers = [];
let currentTeacher = [];
let courseDetail = {};
let topicCur = {};

let allTeacherIds = [];
let currentTeacherId;
let allTopic = [];
let currentTopicId;

const topicId = window.localStorage.getItem("topicId");
console.log(topicId);

const request = async() => {
    const coursreResponse = await fetch(url, header);
    const courseJson = await coursreResponse.json();

    const curTopic = "http://localhost:8080/topics/" + courseJson.topicId;
    const topicRes = await fetch(curTopic, header);
    const topicJson = await topicRes.json();

    const teacherAPI = "http://localhost:8080/topics/" + topicJson.id + "/teachers";
    const teacherResponse = await fetch(teacherAPI, header);
    const teacherJson = await teacherResponse.json();

    //Get student
    const studentResponse = await fetch(studentAPI, header);
    const studentData = await studentResponse.json();

    return [teacherJson, courseJson, topicJson, studentData];
};

request().then(([teachers, courseDetail, topicCur, students]) => {
    allTeachers = teachers;
    allStudents = students;
    currentStudentIds = courseDetail.studentIds;
    students.forEach((student) => {
        if (currentStudentIds.includes(student.id)) {
            currentStudents.push(student)
        } else {
            remainStudents.push(student)
        }
    });

    renderCurrentStudent(currentStudents);
    renderRemainStudents(remainStudents);

    currentTeacherId = courseDetail.teacherId;

    teachers.forEach((teacher) => {
        if (currentTeacherId == teacher.id) {
            currentTeacher.push(teacher);
            console.log(currentTeacherId);
        } else {
            remainTeachers.push(teacher);
        }
    });


    renderCurrentTeacher(currentTeacher);
    renderRemainTeachers(remainTeachers)
    selectTeacher();
    remainTeachers.length = 0;
    currentTeacher.length = 0;
    const startDate = courseDetail.startDate.split("T")[0];
    const endDate = courseDetail.endDate.split("T")[0];
    cname.value = courseDetail.name;
    desc.innerHTML = courseDetail.description;
    code.value = courseDetail.enrollCode;
    date.value = startDate + " - " + endDate;

    teacherId = courseDetail.teacherId;

    console.log("length: ", currentTopic.length);
    for (let i = 0; i < currentTopic.length; i++) {
        if (currentTopic[i].innerHTML === topicCur.name) {
            currentTopic[i].selected = true;
        }
    }

    console.log(currentTopic.value);
    console.log(topicCur.name);
});

const renderRemainTeachers = (remainTeachers) => {
    remainTeacherTable.innerHTML = remainTeachers
        .map(
            (teacher) =>
            `<tr id=${teacher.id}>
          <td>${teacher.id}</td>
          <td>${teacher.firstName} ${teacher.lastName}</td>
          <td>${teacher.email}</td>
        </tr>`
        ).join("");
};




const renderCurrentTeacher = (currentTeacher) => {
    currentTeacherTable.innerHTML = currentTeacher.map(
        (currentTeacher) =>
        `<tr id=${currentTeacher.id}>
          <td>${currentTeacher.id}</td>
          <td>${currentTeacher.firstName} ${currentTeacher.lastName}</td>
          <td>${currentTeacher.email}</td>
          <td><button  onclick="deleteRow(this)" style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close">
          <span aria-hidden="true">&times;</span></button></td>
        </tr>`

    ).join(" ");
    selectTeacher();

};



const renderRemainStudents = (remainStudents) => {
    remainStudentTable.innerHTML = remainStudents
        .map(
            (student) =>
            `<tr onClick="addStudents(${student.id})" id=${student.id}>
          <td>${student.id}</td>
          <td>${student.firstName} ${student.lastName}</td>
          <td>${student.email}</td>
        </tr>`
        ).join("");
};



const renderCurrentStudent = (currentStudents) => {
    currentStudentTable.innerHTML = currentStudents.map(
        (currentStudents) =>
        `<tr id=${currentStudents.id}>
                      <td>${currentStudents.id}</td>
                      <td>${currentStudents.firstName} ${currentStudents.lastName}</td>
                      <td>${currentStudents.email}</td>
                      <td><button  onclick="removeStudents(${currentStudents.id})" style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close">
                      <span aria-hidden="true">&times;</span></button></td>
                    </tr>`

    ).join(" ");

};


const addStudents = (id) => {
    currentStudentIds.push(id);
    remainStudents = remainStudents.filter(student => student.id !== id);
    allStudents.forEach(student => {
        if (student.id === id) {
            currentStudents.push(student);
        }
    });
    renderCurrentStudent(currentStudents);
    renderRemainStudents(remainStudents);
}

const removeStudents = (id) => {
    currentStudentIds.pop(id);
    currentStudents = currentStudents.filter(student => student.id !== id);
    allStudents.forEach(student => {
        if (student.id === id) {
            remainStudents.push(student);
        }
    });
    renderCurrentStudent(currentStudents);
    renderRemainStudents(remainStudents);
}


var startDate;
var endDate;

$('input[name="daterange"]').daterangepicker({
        opens: "left",
    },
    function(start, end, label) {
        startDate = start.format("YYYY-MM-DD");
        endDate = end.format("YYYY-MM-DD");
    }
);

function editCourse() {
    const data = {
        enrollCode: document.getElementById("code").value,
        description: document.getElementById("desc").value,
        startDate: startDate,
        endDate: endDate,
        teacherId: parseInt(currentTeacherId),
        description: document.getElementById("desc").value,
        studentIds: currentStudentIds,
        topicId: parseInt(document.getElementById("topic").value),
        categoryId: parseInt(document.getElementById("cate").value),
        thumbnail: "thumnail.jpg",
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

    fetch(url, other_params)
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                console.log(response);
                return response.json().then((err) => {
                    throw new Error(err.message);
                });
            }
        })
        .then(function(data) {
            alert("Edited course information successfully !");
            window.location.assign("/course/course.html");
        })
        .catch(function(error) {
            alert(error);
        });
    console.log(data);
    return true;
}
const topicUrl = "http://localhost:8080/topics";

const getTopic = async() => {
    const topicRes = await fetch(topicUrl, header);
    const topic = topicRes.json();
    return topic;
};
getTopic().then((allTopic) => {
    document.querySelector("#topic").innerHTML = allTopic
        .map((topic) => ` <option value = "${topic.id}">${topic.name}</option>`)
        .join("");
});

getTeacher = (topicId = document.getElementById("topic").value) => {

    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/topics/" + topicId + "/teachers";
    var bearer = "Bearer " + token;
    fetch(url, {
            method: "get",
            headers: {
                Authorization: bearer,
                "X-FP-API-KEY": "token", //it can be iPhone or your any other attribute
                "Content-Type": "application/json",
            },
        })
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        })
        .then(function(data) {
            data.map((teacher) => {
                if (currentTeacherId === teacher.id) {

                    currentTeacher.push(teacher);
                } else {
                    remainTeachers.push(teacher);
                }
            }).join(" ");
            console.log("day la currnet teacher", currentTeacher);
            console.log("day la reaming teacher", remainTeachers);
            console.log(data);

            renderRemainTeachers(remainTeachers);
            selectTeacher();
            remainTeachers.length = 0;
            currentTeacher.length = 0;
        })
        .catch(function(error) {
            console.log(error);
        });
    return true;
};

function appendData(data) {
    for (var i = 0; i < data.length; i++) {
        var a = remainTeacherTable.insertRow(remainTeacherTable.length),
            id = a.insertCell(0),
            name = a.insertCell(1),
            email = a.insertCell(2);

        name.innerHTML = data[i].firstName + data[i].lastName;
        email.innerHTML = data[i].email;
        id.innerHTML = data[i].id;
    }
}

function getCategory() {
    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/categories";
    var bearer = "Bearer " + token;
    fetch(url, {
            method: "get",
            headers: {
                Authorization: bearer,
                "X-FP-API-KEY": "token", //it can be iPhone or your any other attribute
                "Content-Type": "application/json",
            },
        })
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        })
        .then((data) => {
            document.querySelector("#cate").innerHTML = "";

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    content += `
                <option value = "${item.id}">${item.name}</option>
            `;
                });
                document.querySelector("#cate").innerHTML = content;
            }
        })
        .catch(function(error) {
            console.log(error);
        });
    return true;
}
getCategory();