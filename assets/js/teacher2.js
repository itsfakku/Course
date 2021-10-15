var table = document.getElementById("tableData");
var teacher = document.getElementById("teacher1");
for (var i = 1; i <= table.rows.length; i++) {
  table.rows[i].onclick = function () {
    tableText(this);
  };
}

function tableText(tableRow) {
  var count = 0;
  var name = tableRow.childNodes[1].innerHTML;
  var email = tableRow.childNodes[3].innerHTML;
  var index = -1;
  if (teacher.rows.length <= 10) {
    for (var a = 1; a < teacher.rows.length; a++) {
      if (teacher.rows[a].cells[0].innerHTML == name) {
        index = teacher.rows[a].rowIndex;
      }
    }
    if (index == -1) {
      var btn = `<button style="color: red; opacity : 100%" type="button" id="btnDelete" class="close" aria-label="Close" onclick="deleteRow(this)">
      <span aria-hidden="true">&times;</span></button>`;
      var markup =
        "<tr id ='row'> <td id='name'>" +
        name +
        "</td> <td id='email'>" +
        email +
        "</td><td>" +
        btn +
        "</td></tr>";
      $("#bodyt").append(markup);
      count == count + 1;
    } else {
      alert("This teacher is already assigned !");
    }
  } else {
    alert("Can assign only 1 teacher");
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
    teacher.deleteRow(i);
  } else return false;
}
function checkExisted() {}
