var rIndex,
  tName,
  email,
  table = document.getElementById("tableData"),
  table1 = document.getElementById("teacher1"),
  currTeacher = document.getElementById("bodyteacher"),
  teacher = document.getElementById("bodyt");

selectTeacher();
function selectTeacher() {
  var index = -1;
  for (var i = 1; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {
      rIndex = this.rowIndex;
      tName = this.cells[0].innerHTML;
      email = this.cells[1].innerHTML;
      //teacher <=10
      if (teacher.rows.length <= 10) {
        for (var a = 1; a < teacher.rows.length; a++) {
          if (teacher.rows[a].cells[0].innerHTML == tName) {
            index = a;
          }
        }
        if (index == -1) {
          //delete teacher assigned 
          table.deleteRow(rIndex);
          //add new teacher to topic
          var newRow = teacher.insertRow(teacher.length),
            cell1 = newRow.insertCell(0),
            cell2 = newRow.insertCell(1),
            cell3 = newRow.insertCell(2);
          cell1.innerHTML = tName;
          cell2.innerHTML = email;
          cell3.innerHTML = `<button style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close" onclick="deleteRow(this)">
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
  var x = confirm("Are you sure you want to delete ?");
  if (x) {
    var i = row.parentNode.parentNode.rowIndex;
    console.log(table1.rows[i].cells[0].innerHTML);
    var newRow = currTeacher.insertRow(currTeacher.length),
      o1 = newRow.insertCell(0),
      o2 = newRow.insertCell(1);
    o1.innerHTML = table1.rows[i].cells[0].innerHTML;
    o2.innerHTML = table1.rows[i].cells[1].innerHTML;
    table1.deleteRow(i);
    selectTeacher();
  } else return false;
}

function getRowData() { }
