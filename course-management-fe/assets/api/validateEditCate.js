const cateName = document.getElementById("cname");
const cateNameError = document.getElementById("cnameError");

const validateInput = () => {
    if (cateName.value.length === 0) {
        cateNameError.innerHTML = "Input must be filled"
    } else {
        cateNameError.innerHTML = ""
    }
}


cateName.addEventListener("blur", validateInput)