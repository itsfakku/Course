var createbtn = document.querySelector("create");



function addCategory() {
    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/categories";
    var bearer = "Bearer " + token;

    const data = {
        "name": document.getElementById("cname").value,
        "description": document.getElementById("des").value
            // "image": document.getElementById("image").value,

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
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {
                return response.json().then(error => {throw new Error(error.message)});
            }
        })
        .then(function(data) {
            alert("Created category successfully !")
            window.location = "../category/listCategory.html"
        })
        .catch(function(error) {
            alert(error)
        });
    return true;
}