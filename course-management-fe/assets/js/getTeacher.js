    var table = document.getElementById("tableData");
    for (var i = 0; i < table.rows.length; i++) {
    table.rows[i].onclick = function () {
        tableText(this);
    };
    }
    function tableText(tableRow) {
    var ten = tableRow.childNodes[1].innerHTML;
    var email = tableRow.childNodes[3].innerHTML;
    var obj = { ten: ten, email: email };
    document.getElementById("ten").innerHTML = ten;
    document.getElementById("email").innerHTML = email;
    }
