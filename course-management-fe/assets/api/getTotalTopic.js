function getTotal() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/topics/total";
    var bearer = 'Bearer ' + token;
    fetch(url, {
            method: "GET",
            headers: {
                'Authorization': bearer,
                'X-FP-API-KEY': 'token', 
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
            var total = document.getElementById("totalTopic")
            total.innerHTML = data
           
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getTotal()