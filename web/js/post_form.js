//not actually ajax.
function post_ajax(form_element){
    let hidden = document.getElementById("hidden-form");
    let content_container = document.getElementById("editor-container").children[0];
    hidden.value = content_container.innerHTML;
    return false;
}
let default_quill = "<p><br></p>";
function post_ajax_required(form_element){
    let hidden = document.getElementById("hidden-form");
    let content_container = document.getElementById("editor-container").children[0];
    if (content_container.innerHTML.length == default_quill.length) {
        if (content_container.innerHTML == default_quill) {
            $('#alert-required').show();
            return false;
        }
    }
    hidden.value = content_container.innerHTML;
    return true;
}