			var course = document.getElementById("course");
			var listtopic = document.getElementById("listtopic");
			var listuser = document.getElementById("listuser");
			var listcategory = document.getElementById("listcategory");

			function deletelist(rows)
			{
				var i = rows.parentNode.parentNode.rowindex
				course.deleteRow(i)
            }

			function deletetopic(rows)
			{
				var i = rows.parentNode.parentNode.rowindex
				listtopic.deleteRow(i)
            }

			function deleteuser(rows)
			{
				var i = rows.parentNode.parentNode.rowindex
				listuser.deleteRow(i)
            }

			function deletecategory(rows)
			{
				var i = rows.parentNode.parentNode.rowindex
				listcategory.deleteRow(i)
            }
