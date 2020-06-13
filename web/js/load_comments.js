let comment_div;
let article_id;
let get_comments_url = "./get-comments?article=";
let reveal_comment_url = "./get-comments?comment=";
let admin = false;
let user_id;
let author_id;
window.addEventListener("load",function () {
   comment_div = document.getElementById("comment-section");
   article_id = comment_div.getAttribute("data-article");
   admin = document.getElementById("censor-button") != null;
   user_id = document.getElementById("main-content").getAttribute("data-user-id");
   author_id = document.getElementById("main-content").getAttribute("data-author-id");
   load_comments(article_id);
});
function load_comments(articleID){
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let data = JSON.parse(this.responseText);
            for (let i = 0; i < data.length; i++) {
                fill_top_comments(data[i],comment_div);
            }
        }
    };
    xhr.open("GET",get_comments_url + articleID,true);
    xhr.send();
}
function reveal_redacted_comment(commentID,container){
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let data = JSON.parse(this.responseText);
            wipe(container);
            fill_comments(data,container);
        }
    };
    xhr.open("GET",reveal_comment_url + commentID,true);
    xhr.send();
}
function fill_top_comments(data,parent){
    let box = full_make("div",["c_grid","top-comment-box"],parent);
    fill_comments(data,box);
}
function fill_sub_comments(data,parent){
    let box = full_make("div",["c_grid","inner-comment-box"],parent);
    fill_comments(data,box);
}
function fill_comments(data,box) {
    let person = fmake("a",full_make("div",["person_1"],box));
    person.href = "./profile?user=" + data["authorID"];
    person.onclick = "return false";
    //let person_span = fmake("span",person);
    let avatar = fmake("img",person);
    avatar.setAttribute("src","assets/avatars/" + data["authorAvatar"]);
    avatar.setAttribute("style","width:auto; margin: 10px;");
    let desc = full_make("div",["desc"],box);
    let sub_box = full_make("div",["c_sub_grid"],desc);
    let author_anchor = fmake("a",fmake("p",sub_box));
    author_anchor.href = "#";
    author_anchor.innerHTML = `${data["authorName"]},${data["updated"]}`;
    let links = fmake("span",sub_box);
    let reply = full_make("a",["btn","btn-secondary"],links);
    //reply.href = "#";
    reply.onclick = "return false;";
    reply.innerText = "Reply";
    reply.addEventListener("click",function(){reply2comment(box,data["id"])});
    if (admin || user_id === data["authorID"]+"" || user_id === author_id) {
        make_dropdown(links, box, data);
    }
    fmake("br",sub_box);
    fmake("br",sub_box);
    let para = full_make("div",["para"],desc);
    let content = fmake("div",para);
    content.setAttribute("id",`comment-${data["id"]}`);
    content.innerHTML = data["content"];
    let clear = full_make("div",["clear"],box);
    if (admin){
        let admin_drop = full_make("div",["dropdown","btneditarticle"],links);
        let admin_button = full_make("button",["btn","btn-danger","dropdown-toggle","small-button"],admin_drop);
        admin_button.setAttribute("type","button");
        admin_button.setAttribute("data-toggle","dropdown");
        admin_button.innerText = "censor";
        full_make("span",["caret"],admin_button);
        let admin_list = full_make("ul",["dropdown-menu"],admin_drop);
        let censor = fmake("a",fmake("li",admin_list));
        censor.href = `./censor?type=comment&id=${data["id"]}`;
        censor.innerText = "Censor";
        let uncensor = fmake("a",fmake("li",admin_list));
        uncensor.href = `./free?type=comment&id=${data["id"]}`;
        uncensor.innerText = "Uncensor";
        let show = fmake("a",fmake("li",admin_list));
        show.innerText = "show censored";
        show.href = "javascript:void(0);";
        show.addEventListener("click",function () {
            reveal_redacted_comment(data["id"],box);
        });
    }
    let sub_comments = full_make("div",["c_sub_grid"],box);
    let inner_comments = data["comment"];
    for (let i = 0; i < inner_comments.length; i++) {
        fill_sub_comments(inner_comments[i],sub_comments);
    }
}
function reply2comment(comment,commentID) {
    clear_existing(commentID);
    let input = fmake("form",comment);
    input.setAttribute("id",`pop-comment-${commentID}`);
    input.setAttribute("method","post");
    input.setAttribute("enctype","application/x-www-form-urlencoded");
    input.setAttribute("action","./comment_comment");
    let hidden_input = fmake("input",input);
    hidden_input.setAttribute("type","hidden");
    hidden_input.setAttribute("name","articleID");
    hidden_input.setAttribute("value",`${article_id}`);
    let comment_input = fmake("input",input);
    comment_input.setAttribute("type","hidden");
    comment_input.setAttribute("name","parent");
    comment_input.setAttribute("value",`${commentID}`);
    let text_area = fmake("textarea",input);
    text_area.setAttribute("name","comment");
    let button = full_make("input",["text-area"],input);
    button.setAttribute("type","submit");
    button.setAttribute("value","Reply");
    return false;
}
function make_dropdown(parent,comment,data) {
    let commentID = data["id"];
    let container = full_make("div",["dropdown","btneditarticle"],parent);
    let button = full_make("button",["btn","btn-warning","dropdown-toggle","small-button"],container);
    button.setAttribute("type","button");
    button.setAttribute("data-toggle","dropdown");
    button.innerText = "options";
    full_make("span",["caret"],button);
    let list = full_make("ul",["dropdown-menu"],container);
    let edit = fmake("a",fmake("li",list));
    edit.addEventListener("click",function(){comment_editor(comment,data["id"],data["content"])});
    edit.innerText = "edit";
    let del = fmake("a",fmake("li",list));
    del.setAttribute("href",`./delete_comment?commentID=${commentID}`);
    del.innerText = "delete";
    let purge = fmake("a",fmake("li",list));
    purge.setAttribute("href",`./purge_comment?commentID=${commentID}`);
    purge.innerText = "purge";
}
function comment_editor(comment,commentID,text){
    clear_existing(commentID);
    let input = fmake("form",comment);
    input.setAttribute("id",`pop-comment-${commentID}`);
    input.setAttribute("method","post");
    input.setAttribute("enctype","application/x-www-form-urlencoded");
    input.setAttribute("action",`./edit_comment`);
    let hidden_input = fmake("input",input);
    hidden_input.setAttribute("type","hidden");
    hidden_input.setAttribute("name","commentID");
    hidden_input.setAttribute("value",`${commentID}`);
    let comment_input = fmake("input",input);
    comment_input.setAttribute("type","hidden");
    comment_input.setAttribute("name","parent");
    comment_input.setAttribute("value",`${commentID}`);
    let text_area = fmake("textarea",input);
    text_area.setAttribute("name","content");
    text_area.innerHTML = text;
    let button = fmake("input",input);
    button.setAttribute("type","submit");
    button.setAttribute("value","Edit");
    return false;
}
function clear_existing(commentID) {
    let thing = document.getElementById(`pop-comment-${commentID}`);
    if(!!thing) {
        thing.outerHTML = "";
    }
}