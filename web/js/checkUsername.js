const check_username_url_start = "./check-username?username=";
let input_username = null;
let username_warning = null;
function check_username(username){
    ajax_get(`${check_username_url_start}${username}`, function (responseText) {
        let exists = JSON.parse(responseText);
        if (exists){
            username_warning.classList.replace("invisible","visible");
        } else {
            username_warning.classList.replace("visible","invisible");
        }
    });
}
window.addEventListener("load",function () {
    username_warning = document.getElementById("username-taken");
    input_username = document.getElementById("input_username");
    input_username.addEventListener("input",function () {
        check_username(input_username.value);
    });
});