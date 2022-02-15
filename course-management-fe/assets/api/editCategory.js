var url_default = window.location.search;
id = url_default.replace("?", ''); // remove the ?

token = window.localStorage.getItem("access_token");
const url = "http://localhost:8080/categories/" + id;
var cname = document.getElementById("cname");
var desc = document.getElementById("desc");
var bearer = "Bearer " + token;
getCategory = (id) => {
    fetch(url, {
            method: "GET",
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
            cname.value = data.name;
            desc.innerHTML = data.description;
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}

getCategory(id)
editCategory = (id) => {


    const data = {

        "name": document.getElementById("cname").value,
        "description": document.getElementById("desc").value,

    };


    const other_params = {
        headers: {
            Authorization: bearer,
            "X-FP-API-KEY": "token1",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        method: "PUT",
    };

    fetch(url, other_params)
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {

                return response.json().then(error => { throw new Error(error.message) });
            }
        })
        .then(function(data) {
            window.location.assign("../category/listCategory.html")

            alert("Edited Category !")
        })
        .catch(function(error) {
            alert(error)
        });
    return true;
}