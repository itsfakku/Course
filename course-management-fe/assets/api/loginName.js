let names = window.sessionStorage.getItem('username');
console.log(names)

function getUser() {
    token = window.localStorage.getItem('access_token')
    const name = "http://localhost:8080/users/?username=" + names;
    var bearer = 'Bearer ' + token;
    fetch(name, {
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
            document.getElementById('usernames').innerHTML = data.firstName + ' ' + data.lastName;
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getUser()