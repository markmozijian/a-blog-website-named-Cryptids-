//Stoken from muh pokermens assignment
function wipe(element){
    element.innerHTML = "";
}
function make(thing){
    return document.createElement(thing);
}
function full_make(type,classes,appendee){
    let out = make(type);
    for (let i = 0; i < classes.length; i++) {
        out.classList.add(classes[i]);
    }
    appendee.appendChild(out);
    return out;
}
function fmake(type,appendee){
    return full_make(type,[],appendee);
}
function done(obj){
    return (obj.status == 200 && obj.readyState == 4);
}
function ajax_get(url,action) {
    console.log(url);
    let ajax_request = new XMLHttpRequest();
    ajax_request.onreadystatechange = function () {
        console.log(this.status);
        console.log(this.statusText);
        if (done(this)){
            action(this.responseText);
        }
    };
    ajax_request.open("GET",url,true);
    ajax_request.send();
}