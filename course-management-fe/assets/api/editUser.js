var url_default = window.location.search;
id = url_default.replace("?", ''); // remove the ?

token = window.localStorage.getItem("access_token");
const url = "http://localhost:8080/users/" + id;

var bearer = "Bearer " + token;
getUser = (id) => {
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
        })
        .then(data => {
            var content = `
                    <div class="col-xs-10" style="border: 1px solid #b1a5a5c7; margin-left: 10%;">
                    <div class="form-group" style="margin-left: 40px;"><br>
                        <label  style="float: left;">Role</label><br><br>
                        <div class="form-group">
                            <select class="form-select" disabled aria-label="Default select example"  id="role" style="width: 95%; height: 30px; padding: 3px 4px;" >
                              <option>${data.role}</option>
                            </select>
                        </div>
                    </div>
                                                            
                </div>
                
                <div class="col-xs-10" style="border: 1px solid #b1a5a5c7; margin-left: 10%; margin-top: 20px;">
                    <div class="col-xs-6" style="margin-top: 5px;margin-left: 40px;">
                        <div class="row">
                            <div class="form-group">
                                 <label for="cname">First Name</label> 
                                <input
                                  type="text"
                                  name="fname"
                                  id="fname"
                                  placeholder="Enter First name"
                                  style="width: 100%;"
                                  value="${data.firstName}"
                                /> 
                                
                            
                            </div>
                            <div class="form-group">
                                <label for="cname">Email</label>
                                <input
                                  type="text" disabled
                                  name="email"
                                  id="email"
                                  placeholder="Enter Email"
                                  style="width: 100%;"
                                  value="${data.email}"                          
                                />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-5" style="margin-top: 5px;">
                        <div class="form-group">
                            <div class="form-group">
                                <label for="cname">Last Name</label>
                                <input
                                  type="text"
                                  name="lname"
                                  id="lname"
                                  placeholder="Enter Last name"
                                  style="width: 101%;"
                                  value="${data.lastName}"
                                />
                            </div>
                            <div class="form-group">
                                <label for="cname">Phone</label>
                                <input
                                  type="text"
                                  name="phone"
                                  id="phone"
                                  placeholder="Enter Phone"
                                  style="width: 101%;"
                                  value="${data.phone}"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-11" style="margin-left: 29px; width: 99%;">
                        <label>Date of birth</label>
                        <div class="row">
                            
                            <div class="col-xs-10 col-sm-12" style="width: 94%;" >
                                <div class="input-group">
                                    <input type="date" id="myDate" value="${data.dob}" style="width: 584%;">
                                    <!-- <span class="input-group-addon">
                                        <i class="fa fa-calendar bigger-110"></i>
                                    </span> -->
                                </div>
                            </div>
                        </div><br>
                        <div class="form-group" style="width: 94%;">
                            <label for="cname">Address</label>
                            
                            <!-- <div>
                                <input
                                type="text"
                                name="pass"
                                id="pass"
                                placeholder="Enter Address"
                                />
                                
                            </div> -->
                            <div class="api" style="border: 1px solid #b1a5a5c7;" id="address">
                                <div class="container mx-auto px-5 flex flex-col flex-1">
                                    <div x-data="formApp" class="mt-4">
                                        <div class="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4" style="flex-direction: row; display: flex;">
                                        <div class="relative" @click.outside="provinceListShown = false" style="margin-left: 10%;">
                                            <input
                                            class="p-1 px-2 appearance-none outline-none text-gray-800 border"
                                            x-model.trim="provinceSearch"
                                            placeholder="Tỉnh..."
                                            @focus="startSearchingProvince"
                                            style="padding: 2px; margin-top: 5px; margin-bottom: 5px;" id="cityy" value="${data.address}"/>
                                
                                            <div class="absolute z-10 max-h-48 w-full bg-gray-100 overflow-y-auto shadow" x-show="provinceListShown && filteredProvinces.length" style="max-height: 100px; position: absolute; z-index: 10; background-color: snow; width: 170px; cursor: default; display: none;">
                                            <ul class="list-none" style="list-style-type: none;padding-inline-start: 0px !important;overflow-y: scroll; max-height: 100px; margin-left: auto;">
                                                <template x-for="(item, idx) of filteredProvinces" :key="idx">
                                                <li
                                                    x-html="highlightName(item)"
                                                    class="px-2 py-1 cursor-pointer bg-white hover:bg-blue-100"
                                                    @click="selectProvince(item)"
                                                ></li>
                                                </template>
                                            </ul>
                                            </div>
                                        </div>
                                        <div class="relative" @click.outside="districtListShown = false" style="margin-left: 40px;">
                                            <input
                                            class="p-1 px-2 appearance-none outline-none text-gray-800 border"
                                            x-model.trim="districtSearch"
                                            placeholder="Huyện..."
                                            @focus="startSearchingDistrict"
                                            @input.debounce="searchDistrictOnTyping"
                                            style="padding: 2px; margin-top: 5px; margin-bottom: 5px;"/>
                                
                                            <div class="absolute z-10 max-h-48 w-full bg-gray-100 overflow-y-auto shadow"x-show="districtListShown && filteredDistricts.length" style=" position: absolute; z-index: 10; background-color: snow; width: 170px; cursor: default; display: none;">
                                            <ul class="list-none" style="list-style-type: none;padding-inline-start: 0px !important;overflow-y: scroll; max-height: 100px; margin-left: auto;">
                                                <template x-for="(item, idx) of filteredDistricts" :key="idx">
                                                <li
                                                    x-html="highlightName(item)"
                                                    class="px-2 py-1 cursor-pointer bg-white hover:bg-blue-100"
                                                    @click="selectDistrict(item)"
                                                ></li>
                                                </template>
                                            </ul>
                                            </div>
                                        </div>
                                        <div class="relative" @click.outside="wardListShown = false" style="margin-left: 40px;">
                                            <input
                                            class="p-1 px-2 appearance-none outline-none text-gray-800 border"
                                            x-model.trim="wardSearch"
                                            placeholder="Xã..."
                                            @focus="startSearchingWard"
                                            style="padding: 2px; margin-top: 5px; margin-bottom: 5px;"/>
                                
                                            <div class="absolute z-10 max-h-48 w-full bg-gray-100 overflow-y-auto shadow " x-show="wardListShown && filteredWards.length" style="position: absolute; z-index: 10; background-color: snow; width: 170px; cursor: default; display: none;">
                                            <ul class="list-none" style="list-style-type: none;padding-inline-start: 0px !important;overflow-y: scroll; max-height: 100px; margin-left: auto;">
                                                <template x-for="(item, idx) of filteredWards" :key="idx">
                                                <li
                                                    x-html="highlightName(item)"
                                                    class="px-2 py-1 cursor-pointer bg-white hover:bg-blue-100"
                                                    @click="selectWard(item)"
                                                ></li>
                                                </template>
                                            </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--image-->
                        <div class="widget-body">
                            <label for="cname">Image</label>
                            <div style="width: 100%;">																						
                                <!-- <input type="file" id="id-input-file-2" />																																										 -->
                                <input class="inputimg" multiple="" type="file" id="id-input-file-3"  value="${data.avatar}" />										
                            </div>
                        </div>
                    </div>
                </div>
            `

            document.querySelector("#editInput").innerHTML = content;

        }).catch(function(error) {
            console.log(error)
        });
    return true;
}

getUser(id)
editUser = (id) => {

    const data = {

        "email": document.getElementById("email").value,
        "firstName": document.getElementById("fname").value,
        "lastName": document.getElementById("lname").value,
        "avatar": document.getElementById("id-input-file-3").value,
        "phone": document.getElementById("phone").value,

        "address": document.getElementById("cityy").value,
        "dob": document.getElementById("myDate").value,
        "role": document.getElementById("role").value,

    };


    const other_params = {
        headers: {
            Authorization: bearer,
            "X-FP-API-KEY": "token1",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
        method: "PUT",
    };

    fetch(url, other_params)
        .then(function(response) {
            if (response.ok) {

                return response.json();
            } else {
                throw new Error("Could not reach the API: " + response.statusText);
            }
        })
        .then(function(data) {
            window.location.assign("../account/listUser.html")
            alert("User edited successfully !")
        })
        .catch(function(error) {
            alert("Error !!" + error)
        });
    return true;
}