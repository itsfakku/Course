function getUser() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/users/10/latest";
    var bearer = 'Bearer ' + token;
    fetch(url, {
        method: "get",
        headers: {
            'Authorization': bearer,
            'X-FP-API-KEY': 'token', //it can be iPhone or your any other attribute
            'Content-Type': 'application/json'
        }
    })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        }).then(data => {
            document.querySelector("#newest").innerHTML = '';
            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    content += `
                    <tr>                       
                        <td>
                            <a value="${item.id}" style="text-decoration: none;" href="/account/proFile.html?${item.id}">${item.firstName} ${item.lastName}</a>
                        </td>
                        <td>${item.email}</td>
                    </tr>
                    `
                })
                document.querySelector("#newest").innerHTML = content;
            }
        }).catch(function (error) {
            console.log(error)
        });
    return true;
}
getUser()