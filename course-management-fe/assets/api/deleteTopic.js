deleteTopic = (id) => {
    var option = confirm("Are you sure to DELETE?")
    if (option === true) {
        token = window.localStorage.getItem("access_token");
        const url = "http://localhost:8080/topics/" + id;
        var bearer = "Bearer " + token;
        fetch(url, {
                headers: {
                    Authorization: bearer,
                    "X-FP-API-KEY": "token1",
                    "Content-Type": "application/json",
                },
                method: 'DELETE'
            })
            .then(res => {
                location.reload()
            })
            .then(err => console.log(err))
    }
}