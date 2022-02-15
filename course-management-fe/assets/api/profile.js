let names = window.sessionStorage.getItem('username');
console.log(names)

function getprofile() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/users/?username=" + names;
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

            
            const dobb = data.dob.split("T")[0];

            document.getElementById('username').innerHTML = data.firstName + ' ' + data.lastName;
            document.getElementById('name').innerHTML = data.firstName + ' ' + data.lastName;
            document.getElementById('role').innerHTML = data.role;
            document.getElementById('country').innerHTML = data.address;
            document.getElementById('age').innerHTML = dobb;
            document.getElementById('phone').innerHTML = data.phone;
            document.getElementById('email').innerHTML = data.email;


        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getprofile()


var url_default = window.location.search;
id = url_default.replace("?", ''); // remove the ?
function getProfileById() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/users/" + id;
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

            const dobb = data.dob.split("T")[0];
            document.getElementById('username').innerHTML = data.firstName + ' ' + data.lastName;
            document.getElementById('name').innerHTML = data.firstName + ' ' + data.lastName;
            document.getElementById('role').innerHTML = data.role;
            document.getElementById('country').innerHTML = data.address;
            document.getElementById('age').innerHTML = dobb;
            document.getElementById('phone').innerHTML = data.phone;
            document.getElementById('email').innerHTML = data.email;

        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getProfileById()