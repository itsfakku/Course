disableUser = (id) => {
    var option = confirm("Do you want do disable this user ?")
    if (option === true) {
        token = window.localStorage.getItem("access_token");
        const url = "http://localhost:8080/users/" + id;
        var bearer = "Bearer " + token;
        fetch(url, {
                headers: {
                    Authorization: bearer,
                    "X-FP-API-KEY": "token1",
                    "Content-Type": "application/json",
                },
                method: 'PATCH'
            })
            .then(res => {
                location.reload()
                console.log(data)
            })
            .then(err => console.log(err))
    }
}