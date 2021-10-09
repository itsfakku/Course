var table = document.getElementById("tableData");
var teacher = document.getElementById("teacher1");
for (var i = 1; i <= table.rows.length; i++) {
  table.rows[i].onclick = function () {
    tableText(this);
  };
}
function tableText(tableRow) {
  var count = 1;
  var name = tableRow.childNodes[1].innerHTML;
  var email = tableRow.childNodes[3].innerHTML;
  if (teacher.rows.length <= 1) {
    var btn = "<td><input type='checkbox' name='record'></td>";
    var markup =
      "<tr id='row'>" +
      btn +
      " <td id='name'>" +
      name +
      "</td> <td id='email'>" +
      email +
      "</td></tr>";
    $("#bodyt").append(markup);
    count++;
  } else {
    alert("You can assign only 1 teacher");
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
