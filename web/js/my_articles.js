let article_columns = null;
let my_articles_button = null;
let all_articles_button = null;
let article_view_mode = false;//true means only my articles
let base_article_url = "./all_articles";
let xhr = new XMLHttpRequest();
window.addEventListener("load",function () {
    article_columns = document.getElementsByClassName("article-col");
    get_all_articles();
    my_articles_button = document.getElementById("my-articles-button");
    all_articles_button = document.getElementById("all-articles-button");
    if (all_articles_button != null) {
        all_articles_button.style.display = "none";
    }
});
function resfresh_article_columns(data_set){
    for (let i = 0; i < data_set.length; i++) {
        let data = data_set[i];
        let column = article_columns[i];
        wipe(column);
        populate_article_column(column,data);
        $('.loader').hide();
    }
}
function get_my_articles(user_id) {
    xhr.abort();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let text = this.responseText;
            let data_set = JSON.parse(text);
            resfresh_article_columns(data_set);
        }
    };
    xhr.open("GET",`${base_article_url}?my=${user_id}`,true);
    xhr.send();
    $('.loader').show();
}
function get_all_articles() {
    xhr.abort();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let text = this.responseText;
            let data_set = JSON.parse(text);
            resfresh_article_columns(data_set);
        }
    };
    xhr.open("GET",base_article_url,true);
    xhr.send();
    $('.loader').show();
}
function article_button_toggle(user_id){
    if (article_view_mode){
        get_all_articles();
        all_articles_button.style.display = "none";
        my_articles_button.style.display = "inline-block";
        article_view_mode = false;
    } else {
        get_my_articles(user_id);
        my_articles_button.style.display = "none";
        all_articles_button.style.display = "inline-block";
        article_view_mode = true;
    }
    return false;
}
function populate_article_column(column,data) {
    for (let i = 0; i < data.length; i++) {
        make_article_box(column,data[i]);
    }
}
function make_article_box(column,data) {
    let box = full_make("div",["box-item"],column);
    let pic = full_make("img",["img-responsive"],box);
    pic.src = `assets/mainPics/${data["mainPicture"]}`;
    let content_box = full_make("div",["content"],box);
    let title = fmake("h3",content_box);
    title.innerHTML = data["title"];
    let content = full_make("p",["max-height"],content_box);
    content.innerHTML = data["content"];
    let anchor = fmake("a",content_box);
    anchor.innerText = "Read More...";
    anchor.href = `./view_article?articleID=${data["id"]}`;
    fmake("br",content_box);fmake("br",content_box);
    let span = fmake("span",content_box);
    span.innerHTML = `${data["updated"]} BY ${data["authorName"]}`;
    fmake("br",content_box);
}
let search_base_url = "./search-articles?search=";
function search_articles() {
    let search = $('#search-bar').val();
    xhr.abort();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let text = this.responseText;
            let data_set = JSON.parse(text);
            resfresh_article_columns(data_set);
        }
    };
    xhr.open("GET",search_base_url + search,true);
    xhr.send();
    $('.loader').show();
}
let get_liked_articles_url = "./liked-articles";
function liked_articles(){
    xhr.abort();
    xhr.onreadystatechange = function () {
        if (done(this)){
            let text = this.responseText;
            let data_set = JSON.parse(text);
            resfresh_article_columns(data_set);
        }
    };
    xhr.open("GET",get_liked_articles_url,true);
    xhr.send();
    $('.loader').show();
}