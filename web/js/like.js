// function getDefaultLike() {
//     const iconLike=document.querySelector('.like')
//
// }
function update_like(id) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (done(this)){
            $("#like-counter").text(this.responseText);
        }
    };
    xhr.open("GET",`./like-count?articleID=${id}`);
    xhr.send();
}
function editLike() {

    document.querySelector(".like").addEventListener('click',function(){
        let articleID = window.location.search.split("=")[1];
        let userID=document.querySelector('.like').getAttribute('id');
        let status= document.querySelector(".like").classList.contains('active')?0:1;
        $.post("./LikeIcon",
            {//user useid
                "articleID":articleID,
                "userID":userID,
                "status":status
            },
            function(data,status){
                //data=Number.parseInt(data)
                console.log(data);
            if(data == 0){
                document.querySelector(".like").classList.remove('active')
            }else if(data == 2){
                document.querySelector(".like").classList.remove('active')
            }else if(data==1){
                document.querySelector(".like").classList.add('active')
            }
            update_like(articleID);

        });
    })
}


window.onload=function(){
    // confirm if the like button has been clicked or not when the page loaded
    let articleID = window.location.search.split("=")[1];
    let userID=document.querySelector('.like').getAttribute('id');

    $.post("./LikeStatus",
        {
            "articleID":articleID,
            "userID":userID
        },
        function(data,status){
            //data=Number.parseInt(data)

        console.log(data);
            if(data == 0){
                document.querySelector(".like").classList.remove('active')
            }else if(data == 2){
                document.querySelector(".like").classList.remove('active')
            }else if(data==1){
                document.querySelector(".like").classList.add('active')
            }
    });



    //  按钮点击点赞功能
    editLike()
};
