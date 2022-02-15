function getUser() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/topics";
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
            document.querySelector("#listtopic").innerHTML = '';

            if (data.length > 0) {
                var content = ``;
                data.map((item, index) => {
                    content += `
                <tr> 
                <tr>

                <td> ${++index}</td>
                <td class="hidden-480">${item.name}</td>
                <td>${item.description}</td>

                <td>
                    <div class="hidden-sm hidden-xs action-buttons">
                        <a class="green" href="editTopic.html?${item.id}">
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
                                    <a href="editTopic(${item.id}))" class="tooltip-success" data-rel="tooltip" title="Edit">
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
                })
                document.querySelector("#listtopic").innerHTML = content;
            }
        }).catch(function(error) {
            console.log(error)
        });
    return true;
}
getUser()