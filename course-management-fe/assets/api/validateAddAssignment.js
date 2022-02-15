const assignmentName = document.getElementById("cname");
const assignmentNameError = document.getElementById("cnameError");
const assignmentQuantity = document.getElementById("numberFile");
const assignmentQuantityError = document.getElementById("fileError");
const assignmentFile= document.getElementById("fileFormat");
const assignmentFileError = document.getElementById("fileformatErr");

const validateInput = () => {
    if (assignmentName.value.length === 0) {
        assignmentNameError.innerHTML = "Input must be filled"
    } else {
        assignmentNameError.innerHTML = ""
    }
}
assignmentName.addEventListener("blur", validateInput)

const validateInput1 = () => {
    if (assignmentQuantity.value.length === 0) {
        assignmentQuantityError.innerHTML = "Input must be filled"
    } else {
        assignmentQuantityError.innerHTML = ""
    }
}
assignmentQuantity.addEventListener("blur", validateInput1)

const validateInput2 = () => {
    if (assignmentFile.value.length === 0) {
        assignmentFileError.innerHTML = "Input must be filled"
    } else {
        assignmentFileError.innerHTML = ""
    }
}
assignmentFile.addEventListener("blur", validateInput2)