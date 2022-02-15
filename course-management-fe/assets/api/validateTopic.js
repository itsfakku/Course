const topicName = document.getElementById("cname");
const topicNameError = document.getElementById("cnameError");
// const assign = document.getElementById("bodyt");
// const assignError = document.getElementById("bodytError");


const validateInput = () => {
    if (topicName.value.length === 0) {
        topicNameError.innerHTML = "Input must be filled"
    } else {
        topicNameError.innerHTML = ""
    }
}
topicName.addEventListener("blur", validateInput)

// const validateInput1 = () => {
//     if (assign.rows.length === 0) {
//         assignError.innerHTML = "Input must be filled"
//     } else {
//         assignError.innerHTML = ""
//     }
// }
// assign.addEventListener("blur", validateInput1)
