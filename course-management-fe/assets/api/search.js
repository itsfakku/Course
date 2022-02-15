search_user = () => {
    var dataInput = parseInt(document.getElementById("input_search").value)
    console.log(dataInput);
    token = window.localStorage.getItem("access_token");
    const url = "http://localhost:8080/users/" + dataInput;

    var bearer = "Bearer " + token;
    getUserbyID = (dataInput) => {
        fetch(url, {
            method: "GET",
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
            })
            .then(data => {
                console.log(data);
                document.querySelector("#listuser").innerHTML = `
                <tr>
                <td class="center">
                    <label class="pos-rel">
                        <input type="checkbox" class="ace" />
                        <span class="lbl"></span>
                    </label>
                </td>

                <td>${data.id}</td>
                <td>${data.role}</td>
                <td class="hidden-480">${data.firstName} ${data.lastName}</td>

                <td>${data.email}</td>
                <td>${data.phone}</td>
                <td>${data.address}</td>
                <td>${data.status}</td>

                <td>
                    <div class="hidden-sm hidden-xs action-buttons">
                        <a class="blue" >
                            <i class="ace-icon fa fa-search-plus bigger-130"></i>
                        </a>

                        <a class="green" href="editUser.html?${data.id}">
                            <i class="ace-icon fa fa-pencil bigger-130"></i>
                        </a>

                        <a class="red" href="#" onclick="deleteUser(${data.id})">
                            <i class="ace-icon fa fa-trash-o bigger-130"></i>
                        </a>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-yellow dropdown-toggle"
                                data-toggle="dropdown" data-position="auto">
                                <i
                                    class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul
                                class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip"
                                        title="View">
                                        <span class="blue">
                                            <i
                                                class="ace-icon fa fa-search-plus bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a  onclick="editUser(${data.id})" class="tooltip-success"
                                        data-rel="tooltip" title="Edit">
                                        <span class="green">
                                            <i
                                                class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a class="red" href="#" onclick="deleteUser(${data.id})">
                                        <span class="red">
                                            <i
                                                class="ace-icon fa fa-trash-o bigger-120"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
                `;
               
            })
    }
    getUserbyID(dataInput)

}
