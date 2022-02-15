const courseName = document.getElementById("cname");
const courseNameError = document.getElementById("cnameError");
const courseCode = document.getElementById("code");
const courseCodeError = document.getElementById("courseCodeError");

const validateInput = () => {
    if (courseName.value.length === 0) {
        courseNameError.innerHTML = "Input must be filled"
    } else {
        courseNameError.innerHTML = ""
    }
}
courseName.addEventListener("blur", validateInput)

const validateInput1 = () => {
    if (courseCode.value.length === 0) {
        courseCodeError.innerHTML = "Input must be filled"
    } else {
        courseCodeError.innerHTML = ""
    }
}
courseCode.addEventListener("blur", validateInput1)