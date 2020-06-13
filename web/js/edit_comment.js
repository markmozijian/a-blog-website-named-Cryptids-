function edit_comment(commentID){
    var comment = document.getElementById("comment-" + commentID);
    var content = comment.innerText;
    var commentEditor = document.getElementById("comment-editor-" + commentID);
    commentEditor.hidden = false;
}