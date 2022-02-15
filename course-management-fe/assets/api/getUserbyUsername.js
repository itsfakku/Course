function addUser() {

    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/users/?username";
    var bearer = "Bearer " + token;

    const data = {
        "email": document.getElementById("email").value,
        "avatar": document.getElementById("id-input-file-3").value,
        "phone": document.getElementById("phone").value,

        "address": document.getElementById("cityy").value,
        "dob": document.getElementById("myDate").value,
        "role": document.getElementById("role").value
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
        .then(res => {
            if (res.status === 404) {
                alert("User email can not be empty !")
            } else {
                alert("User added successfully !")
                window.location = "listUser.html"
            }
        })
        .catch(err => alert(err.message))
    return true;
}