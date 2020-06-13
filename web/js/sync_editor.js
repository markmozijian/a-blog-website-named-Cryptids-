let editor_container ;
let input_text_area;
function synchronise(){
    editor_container.onsubmit = function () {
        input_text_area.value = editor_container.innerHTML;
    };
}