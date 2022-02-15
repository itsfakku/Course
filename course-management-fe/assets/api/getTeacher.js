var rIndex,
    tName,
    email,
    table = document.getElementById("tableData"),
    table1 = document.getElementById("teacher1"),
    currTeacher = document.getElementById("bodyteacher"),
    teacher = document.getElementById("bodyt");
cname = document.getElementById("cname");
desc = document.getElementById("desc");

var createbtn = document.querySelector("#create");
const selectTeacherIds = [];


function getTeacher() {
    token = window.localStorage.getItem('access_token')
    const url = "http://localhost:8080/users/role/teacher";
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
        }).then(function(data) {
                appendData(data);
            }

        ).catch(function(error) {
            console.log(error)
        });
    return true;
}
getTeacher()

function appendData(data) {

    for (var i = 0; i < data.length; i++) {
        var a = currTeacher.insertRow(currTeacher.length),
            id = a.insertCell(0),
            name = a.insertCell(1),
            email = a.insertCell(2);

        name.innerHTML = data[i].firstName + data[i].lastName;;
        email.innerHTML = data[i].email;
        id.innerHTML = data[i].id;
        selectTeacher();
    }

}



selectTeacher();

function selectTeacher() {


    var index = -1;
    for (var i = 1; i < table.rows.length; i++) {
        table.rows[i].onclick = function() {
            rIndex = this.rowIndex;
            id = this.cells[0].innerHTML;
            tName = this.cells[1].innerHTML;
            email = this.cells[2].innerHTML;
            //teacher <=10
            if (teacher.rows.length <= 10) {
                for (var a = 1; a < teacher.rows.length; a++) {
                    if (teacher.rows[a].cells[0].innerHTML == id) {
                        index = a;
                    }
                }
                if (index == -1) {
                    //delete teacher assigned 
                    table.deleteRow(rIndex);
                    //add new teacher to topic
                    var newRow = teacher.insertRow(teacher.length);
                    selectTeacherIds.push(parseInt(id));

                    console.log(selectTeacherIds)
                    newRow.id = id;
                    cell1 = newRow.insertCell(0);
                    cell2 = newRow.insertCell(1);
                    cell3 = newRow.insertCell(2);
                    cell4 = newRow.insertCell(3);
                    cell1.innerHTML = id;
                    cell2.innerHTML = tName;
                    cell3.innerHTML = email;
                    cell4.innerHTML = `<button style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close" onclick="deleteRow(this)">
          <span aria-hidden="true">&times;</span></button>`;
                } else {
                    alert("teacher existed");
                }
            } else {
                alert("Can assign only 1 teacher");
            }
        };
    }
}

function Search() {
    var searchValue = document.getElementById("search").value; //get value from textBox by ID Field onkeyUp function
    var searchTable = document.getElementById("tableData"); //Search Value In Table search Table by Id
    var searchColCount; //Column Counetr
    //Loop through table rows
    for (var rowIndex = 0; rowIndex < searchTable.rows.length; rowIndex++) {
        var rowData = "";
        //Get column count from header row
        if (rowIndex == 0) {
            searchColCount = searchTable.rows.item(rowIndex).cells.length;

            continue; //do not execute further code for header row.
        }
        //Process data rows. (rowIndex >= 1)
        for (var colIndex = 0; colIndex < searchColCount; colIndex++) {
            rowData += searchTable.rows
                .item(rowIndex)
                .cells.item(colIndex).textContent;
        }
        //If search term is not found in row data
        //then hide the row, else show
        if (rowData.indexOf(searchValue) == -1)
            searchTable.rows.item(rowIndex).style.display = "none";
        else searchTable.rows.item(rowIndex).style.display = "table-row";
    }
}

function deleteRow(row) {
    var x = deleteRow
    if (x) {
        var i = row.parentNode.parentNode.rowIndex;
        // console.log(table1.rows[i].cells[0].innerHTML);
        var newRow = currTeacher.insertRow(currTeacher.length),
            o1 = newRow.insertCell(0),
            o2 = newRow.insertCell(1);
        o3 = newRow.insertCell(2);
        o1.innerHTML = table1.rows[i].cells[0].innerHTML;
        o2.innerHTML = table1.rows[i].cells[1].innerHTML;
        o3.innerHTML = table1.rows[i].cells[2].innerHTML;
        let removedTeacherIds = parseInt(newRow.id);
        selectTeacherIds.pop(removedTeacherIds);
        console.log(selectTeacherIds);
        table1.deleteRow(i);
        selectTeacher();
    } else return false;
}

function getRowData() {}