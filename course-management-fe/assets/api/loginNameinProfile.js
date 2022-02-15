console.log(names)

function getUser() {
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
            document.getElementById('usernames').innerHTML = data.firstName + ' ' + data.lastName;
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}

getUser()

