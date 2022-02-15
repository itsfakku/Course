function getCourse() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/courses/";
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
            data.sort((a, b) => b.id - a.id);
            document.querySelector("#listCourse").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    const createdDate = item.createdDate.split("T")[0];

                    if (item.status == "Done") {
                        content += `
                    <tr>
                    <td> ${++index} </td>
                    <td class="hidden-480">${item.name}</td>
                    <td>${createdDate} </td>
                    <td> ${item.teacherName} </td>
                    <td> ${item.topicName}</td>
                    <td id="status">${item.status}</td>
                    
                    

                    <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <a class="blue" href="courseDetail.html?${item.id}">
                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
                            </a>

                            <a class="green" href="editCourse.html?${item.id}" id="EditButton" style="display: none";>
                                <i class="ace-icon fa fa-pencil bigger-130"></i>
                            </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                            <div class="inline pos-rel">
                                <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                        <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                            <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="editCourse.html?${item.id}" class="tooltip-success" data-rel="tooltip" title="Edit" >
                                            <span class="green">
                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
            `
                    } else if (item.status == "Teaching") {
                        content += `
                    <tr>
                    <td> ${++index} </td>
                    <td class="hidden-480">${item.name}</td>
                    <td>${createdDate} </td>
                    <td> ${item.teacherName} </td>
                    <td> ${item.topicName}</td>
                    <td id="status">${item.status}</td>
                    
                    

                    <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <a class="blue" href="courseDetail.html?${item.id}">
                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
                            </a>

                            <a class="green" href="editCourse.html?${item.id}" id="EditButton" style="display: none";>
                                <i class="ace-icon fa fa-pencil bigger-130"></i>
                            </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                            <div class="inline pos-rel">
                                <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                        <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                            <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="editCourse.html?${item.id}" class="tooltip-success" data-rel="tooltip" title="Edit" >
                                            <span class="green">
                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
            `
                    } else {
                        content += `
                    <tr>
                    <td> ${++index} </td>
                    <td class="hidden-480">${item.name}</td>
                    <td>${createdDate} </td>
                    <td> ${item.teacherName} </td>
                    <td> ${item.topicName}</td>
                    <td id="status">${item.status}</td>
                    
                    

                    <td>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <a class="blue" href="courseDetail.html?${item.id}">
                                <i class="ace-icon fa fa-search-plus bigger-130"></i>
                            </a>

                            <a class="green" href="editCourse.html?${item.id}" id="EditButton">
                                <i class="ace-icon fa fa-pencil bigger-130"></i>
                            </a>
                        </div>

                        <div class="hidden-md hidden-lg">
                            <div class="inline pos-rel">
                                <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                    <i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                        <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                            <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="editCourse.html?${item.id}" class="tooltip-success" data-rel="tooltip" title="Edit" >
                                            <span class="green">
                                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                        </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>
            `
                    }



                })
                document.querySelector("#listCourse").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getCourse()