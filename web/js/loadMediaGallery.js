let images_div,embed_div,mainPic_div,media_div;
let base_image_url = "./get-article-images";
let base_video_url = "./get-article-videos";
let base_mainPic_url = "./get-main-images";
let base_media_url = "./get-article-media";
let user_data_id;
let is_admin = false;

window.addEventListener("load",function () {
    images_div = document.getElementById("images");
    embed_div = document.getElementById("videos");
    mainPic_div = document.getElementById("mainPics");
    media_div = document.getElementById("media");
    user_data_id = document.getElementById("main-content").getAttribute("data-user-id");
    if (user_data_id < 0){
        is_admin = true;
    }
    load_all_media();
});
function load_all_media() {
    load_all_images();
    load_all_videos();
    load_all_mainPics();
    load_all_mainMedia();
}
function load_all_videos() {
    ajax_get(base_video_url,function (text) {
        let data = JSON.parse(text);
        fill_videos(data);
    });
}
function load_all_images() {
    ajax_get(base_image_url,function (text) {
        let data = JSON.parse(text);
        fill_images(data);
    });
}
function load_author_media(authorID) {
    load_author_images(authorID);
    load_author_videos(authorID);
    load_author_mainPics(authorID);
    load_author_mainMedia(authorID);
}
function load_author_images(authorID) {
    ajax_get(base_image_url + `?author=${authorID}`,function (text) {
        let data = JSON.parse(text);
        fill_images(data);
    });
}
function load_author_videos(authorID) {
    ajax_get(base_video_url + `?author=${authorID}`,function (text) {
        let data = JSON.parse(text);
        fill_videos(data);
    });
}
function load_all_mainPics() {
    ajax_get(base_mainPic_url,function (text) {
        let data = JSON.parse(text);
        fill_mainPics(data,false);
    });
}
function load_all_mainMedia() {
    ajax_get(base_media_url,function (text) {
        let data = JSON.parse(text);
        fill_media(data,false);
    });
}
function load_author_mainPics(authorID) {
    ajax_get(base_mainPic_url + `?author=${authorID}`,function (text) {
        let data = JSON.parse(text);
        fill_mainPics(data,true);
    });
}
function load_author_mainMedia(authorID) {
    ajax_get(base_media_url + `?author=${authorID}`,function (text) {
        let data = JSON.parse(text);
        fill_media(data,true);
    });
}
function fill_images(data) {
    wipe(images_div);
    for (let i = 0; i < data.length; i++) {
        let div = full_make("div",["gallery"],images_div);
        div.innerHTML = data[i];
    }
}
function fill_videos(data) {
    wipe(embed_div);
    for (let i = 0; i < data.length; i++) {
        let div = full_make("div",["gallery"],embed_div);
        div.innerHTML = data[i];
    }
}
function fill_mainPics(data,mine) {
    wipe(mainPic_div);
    for (let i = 0; i < data.length; i++) {
        let div = full_make("div",["gallery"],mainPic_div);
        let picName = data[i];
        let img_tag = fmake("img",div);
        img_tag.setAttribute("src","assets/mainPics/" + picName);
        if (mine || is_admin && (picName != "main-bg.jpg")) {
            fmake("br",div);
            let delete_button = full_make("a", ["button"], div);
            delete_button.setAttribute("href", `./delete-main-picture?file=${picName}`);
            delete_button.innerText = "Delete This";
        }
    }
}
function fill_media(data,mine) {//assume it's a video. video players can play audio too i hope
    wipe(media_div);
    for (let i = 0; i < data.length; i++) {
        let div = full_make("div",["gallery"],media_div);
        let mediaName = data[i];
        let mediaType = mediaName.split(".")[1];
        let video_tag = full_make("video",["videoInsert"],div);
        video_tag.setAttribute("controls","");
        video_tag.setAttribute("style","max-width: 100%");
        let sauce_tag = fmake("source",video_tag);
        sauce_tag.setAttribute("src","assets/media/" + mediaName);
        sauce_tag.setAttribute("type","video/" + mediaType);
        let video_failed = document.createTextNode("Something went wrong with loading the video player");
        video_tag.appendChild(video_failed);
        if (mine || is_admin) {
            fmake("br",div);
            let delete_button = full_make("a", ["button"], div);
            delete_button.setAttribute("href", `./delete-media?file=${mediaName}`);
            delete_button.innerText = "Delete This";
        }
    }
}