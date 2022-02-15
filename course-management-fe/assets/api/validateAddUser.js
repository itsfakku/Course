const email = document.getElementById("email");
const emailError = document.getElementById("emailError");

const validateInput = () => {
    if (email.value.length === 0) {
        emailError.innerHTML = "Email must be filled"
    } else {
        emailError.innerHTML = ""
    }
}


email.addEventListener("blur", validateInput)