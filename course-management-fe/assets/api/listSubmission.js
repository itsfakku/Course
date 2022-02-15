var url_default = window.location.search;
id = url_default.replace("?", '');
window.localStorage.setItem("asmId", id);

function getSubmisstion() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/assignments/" + id + "/submissions";
    var bearer = 'Bearer ' + token;
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
        }).then(data => {
            document.querySelector("#listgrade").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    let gradeStatus = item.gradeStatus === 0 ? "Not Grade" : "Graded";
                    if (item.fileName === '' || item.fileName === null || item.submitStatus === 'Late') {
                        content += `
                    <tr>
                        <td>
                            ${item.studentId}
                        </td>

                        <td class="hidden-480">${item.studentName}</td>
                        <td>
                             <a target="_blank" >${item.fileName}</a></td>
                        <td>${item.submitStatus}</td>
                        <td>${item.grade}</td>
                        <td>${gradeStatus}</td>

                        <!-- class=hidden-sm hidden-xs action-buttons-->
                        <td>
                            <div class="">
                                <a href="grade.html?${item.studentId}">
                                    <button id="gradeBtn" class="btn btn-white btn-info btn-round" style="display: none">
                                        <i class="ace-icon glyphicon glyphicon-check blue"></i>
                                        Submit grade
                                    </button>
                                </a>
                            </div>
                        </td>
                    </tr>
                    `
                    } else {
                        content += `
                    <tr>
                        <td>
                            ${item.studentId}
                        </td>

                        <td class="hidden-480">${item.studentName}</td>
                        <td>
                             <a target="_blank" >${item.fileName}</a></td>
                        <td>${item.submitStatus}</td>
                        <td>${item.grade}</td>
                        <td>${gradeStatus}</td>

                        <!-- class=hidden-sm hidden-xs action-buttons-->
                        <td>
                            <div class="">
                                <a href="grade.html?${item.studentId}">
                                    <button id="gradeBtn" class="btn btn-white btn-info btn-round">
                                        <i class="ace-icon glyphicon glyphicon-check blue"></i>
                                        Submit grade
                                    </button>
                                </a>
                            </div>
                        </td>
                    </tr>
                    `
                    }


                })
                document.querySelector("#listgrade").innerHTML = content;

            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getSubmisstion()