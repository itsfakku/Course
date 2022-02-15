selectStudent();

function selectStudent() {
    var index = -1;
    for (var i = 1; i < table.rows.length; i++) {
        table.rows[i].onclick = function() {
            console.log(teacher)
            rIndex = this.rowIndex;
            id = this.cells[0].innerHTML
            tName = this.cells[1].innerHTML;
            email = this.cells[2].innerHTML;
            //teacher <=10
            if (teacher.rows.length < 50) {
                for (var a = 1; a < teacher.rows.length; a++) {
                    if (teacher.rows[a].cells[0].innerHTML == tName) {
                        index = a;
                    }
                }
                if (index == -1) {
                    //delete teacher assigned 
                    table.deleteRow(rIndex);
                    //add new teacher to topic
                    var newRow = teacher.insertRow(teacher.length);
                    newRow.id = id;
                    TeacherId = id;
                    cell1 = newRow.insertCell(0),
                        cell2 = newRow.insertCell(1),
                        cell3 = newRow.insertCell(2);
                    cell4 = newRow.insertCell(3);

                    cell1.innerHTML = id;
                    cell2.innerHTML = tName;
                    cell3.innerHTML = email;
                    cell4.innerHTML = `<button style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close" onclick="deleteRow(this)">
          <span aria-hidden="true">&times;</span></button>`;
                } else {
                    alert("student existed");
                }
            }
        };
    }
}