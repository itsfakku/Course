function checkUser() {
    // document.getElementById('message').className = "text-success";
    // document.getElementById('message').innerHTML = "checking";

    const url = "http://localhost:8080/authenticate";
    const data = {
        "username": document.getElementById('username').value,
        "password": document.getElementById('pwd').value,
    };
    const other_params = {
        headers: { "content-type": "application/json; charset=UTF-8" },
        body: JSON.stringify(data),
        method: "POST",
        mode: "cors"
    };

    fetch(url, other_params)
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        }).then(function(data) {
            window.localStorage.setItem('access_token', data.jwtToken)
            window.sessionStorage.setItem("username", data.username)
            checkLogin(data.username)
            // window.location = "index.html"
            
        }).catch(function(error) {
            document.getElementById("message").innerHTML = "Wrong password or username";
            document.getElementById("message").className = "text-danger"
        });
    return true;
}
async function checkLogin(username){
    const url = "http://localhost:8080/users/?username=" +username;
    token = window.localStorage.getItem('access_token')
    var bearer = 'Bearer ' + token;
    const other_params = {
        headers: { "content-type": "application/json; charset=UTF-8",'Authorization': bearer,
        'X-FP-API-KEY': 'token'},
        method: "GET",
        mode: "cors"
    };
    const response = await fetch(url, other_params);
    const data = await response.json();
    console.log(data)
    if(data.role === "admin"){
        window.location = '/index.html'
    }
    else if(data.role === "staff"){
        window.location = '/index.html'
    }
    else if(data.role === "teacher"){
        window.location = '/course/myCourseTeacher.html'
    }
    else if (data.role === "student"){
        window.location = '/course/myCourseStu.html'

    }
}


