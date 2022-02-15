var createbtn = document.querySelector("#create");
var currTeacher = document.getElementById("addbody");
var startDate;
var endDate;

    

    $('input[name="daterange"]').daterangepicker({
            opens: "left",
        },
        function(start, end, label) {
            
            startDate = start.format("YYYY-MM-DD");
            endDate = end.format("YYYY-MM-DD");
        },
        
    );



function addCourse() {
    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/courses";
    var bearer = "Bearer " + token;
    
    const data = {
    
    
    "name": document.getElementById("cname").value ,
    "topicId": document.getElementById("topic").value,
    "categoryId": document.getElementById("cate").value,
    "enrollCode": document.getElementById("code").value,
    "description": document.getElementById("des").value,
    "startDate": startDate,
    "endDate": endDate,
    "thumbnail":"",
    "teacherId": TeacherId,
    };

    
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
      .then(function (response) {
        if (response.ok) {
  
          return response.json();
        } else {
            return response.json().then(error => {throw new Error(error.message)});
        }
      })
      .then(function (data) {
  
        alert("added course successfully")
        window.location = "../course/course.html"
  
      })
      .catch(function (error) {
        alert(error)
      });
    return true;
}

function getTopic() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/topics";
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
        })
        .then(data => {
            document.querySelector("#topic").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                
                data.map((item, index) => {
                    topicId=item.id;
                    content += `
                        
                        <option value = "${item.id}">${item.name}</option>
                       
                
            `
                })
                document.querySelector("#topic").innerHTML = content;
            }
            
        })
        .catch(function(error) {
            console.log(error)
        });
    return true;
}
getTopic()

   
getTeacher = (select = document.getElementById("topic").value) => {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/topics/" + select +"/teachers";
    var bearer = 'Bearer ' + token;
    console.log(url)
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
        }).then(function (data) {

            console.log(data)
            $('#addbody').empty();
            appendData(data);
            selectTeacher();

        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getTeacher()
function appendData(data) {

    for (var i = 0; i < data.length; i++) {
      var a = currTeacher.insertRow(currTeacher.length),
        id = a.insertCell(0),
        name = a.insertCell(1),
        email = a.insertCell(2);
  
      name.innerHTML = data[i].firstName + data[i].lastName;;
      email.innerHTML = data[i].email;
      id.innerHTML = data[i].id;
    }   
}

function getCategory() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/categories";
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
            document.querySelector("#cate").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    content += `
                <option value = "${item.id}">${item.name}</option>
            `
                })
                document.querySelector("#cate").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getCategory()

