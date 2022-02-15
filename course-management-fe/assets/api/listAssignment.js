var url_default = window.location.search;
id = url_default.replace("?", '');
window.localStorage.setItem('courseId', id);

function getAssignment() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/courses/" + id + "/assignments";
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
            document.querySelector("#assignment").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    const createdDate = item.dueDate.split("T")[0];
                    content += `
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="cname" style="color: #009688" value="${item.id}">${item.assignmentName}</label>
                        <button id="editAsm" class="btn btn-white btn-success btn-round" style="float: right; padding: 1px;">
                            <a href="editAssignment.html?${item.id}" style="text-decoration: none; color: #81a87b;">
                                <i class=" ace-icon fa fa-pencil bigger-130"></i>
                                
                            </a>
                        </button>
                        <div style="margin-left: 20px;"><br>
                            <a href="assignment.html?${item.id}" style="text-decoration: none; color: tomato;">
                                <i class=" ace-icon fa fa-file-pdf-o"></i> ${item.assignmentName}
                            </a> 
                            <a href="listGrade.html?${item.id}">
                            <button id="manageAsm" class="btn btn-info" style="margin-left: 70%"> Manage assignment 
                            <i class="ace-icon fa fa-print  align-top bigger-125 icon-on-right"></i>
							</button>
                            </a>
                            <b class="" style="margin-left: 2%; color: #009688"> Due date : ${createdDate} </b> <br><br>
                            
                        </div>
                    </div>
                `
                })
                document.querySelector("#assignment").innerHTML = content;
            }

        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getAssignment()