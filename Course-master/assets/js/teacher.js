var table = document.getElementById("tableData");
for (var i = 1; i <= table.rows.length; i++) {
  table.rows[i].onclick = function () {
    tableText(this);
  };
}
function tableText(tableRow) {
  var name = tableRow.childNodes[1].innerHTML;
  var email = tableRow.childNodes[3].innerHTML;
  document.getElementById("name").innerHTML = name;
  document.getElementById("email").innerHTML = email;
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