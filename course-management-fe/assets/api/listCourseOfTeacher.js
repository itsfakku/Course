function getCourseOfTeacher() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/users/teacher/courses";
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
            document.querySelector("#getCourse").innerHTML = '';
            
            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    content += `
                    <tr>
                    <td>
                        <a href="/course/courseDetail.html?${item.id}">${item.id}</a>
                    </td>
                    <td>${item.name}</td>

                    <td class="hidden-480">
                        ${item.status}
                    </td>
                    <td>
                        <a href="/course/editCourseTeacher.html?${item.id}" style="text-decoration: none; color: #81a87b;">
                            <i class=" ace-icon fa fa-pencil"></i> Edit
                        </a>
                    </td>
                </tr>
            `
                })
                document.querySelector("#getCourse").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getCourseOfTeacher()