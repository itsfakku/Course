const username = sessionStorage.getItem('username')
async function checkLogin(){
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
    if(data.role === "admin"){
        document.getElementById("My course teacher").style.display = "none";
        document.getElementById("My course").style.display = "none";
        document.getElementById("submitfiles").style.display = "none";
    }
    else if(data.role === "staff"){
        document.getElementById("My course teacher").style.display = "none";
        document.getElementById("My course").style.display = "none";
        // document.getElementById("submitfiles").style.display = "none";

        document.getElementById("btnAddUser").style.display = "none";
        var a = document.querySelectorAll("#btnEditUser");
        for(var i = 0; i < a.length; i++){
            a[i].style.display = "none"
        };

        var b = document.querySelectorAll("#btnDisableUser");
        for(var i = 0; i < b.length; i++){
            b[i].style.display = "none"
        };
    }
    else if(data.role === "teacher"){
        document.getElementById("dashboard").style.display = "none",
        document.getElementById("category").style.display = "none",
        document.getElementById("topics").style.display = "none",
        document.getElementById("course").style.display = "none",
        document.getElementById("user").style.display = "none",
        document.getElementById("My course").style.display = "none",
        document.getElementById("submitfiles").style.display = "none"
    }
    else if (data.role === "student"){
        
        document.getElementById("dashboard").style.display = "none",
        document.getElementById("category").style.display = "none",
        document.getElementById("topics").style.display = "none",
        document.getElementById("course").style.display = "none",
        document.getElementById("user").style.display = "none",
        document.getElementById("My course teacher").style.display = "none",
        document.getElementById("addAsm").style.display = "none";
        
        var c = document.querySelectorAll("#editAsm");
        for(var i = 0; i < c.length; i++){
            c[i].style.display = "none"
        };

        
        var x = document.querySelectorAll("#manageAsm");
        for(var y = 0; y < x.length; y++){
            x[y].style.display = "none"
        }

    }
}
checkLogin()