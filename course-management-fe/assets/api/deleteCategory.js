deletecategory = (id) => {
    var option = confirm("Are you sure to DELETE?")
    if (option === true) {
        token = window.localStorage.getItem("access_token");
        const url = "http://localhost:8080/categories/" + id;
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
                if (res.status === 400) {
                    alert("Category contains 1 or more courses cannot delete")
                } else {
                    location.reload();
                }
            })
            .catch(err => alert(err.message))
    }
}