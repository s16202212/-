function post() {
   var questionId = $("#question_id").val();
   var content = $("#comment_content").val();
   if(!content){
       alert("不能回复空内容");
       return;
   }
   $.ajax({
       type: "POST",
       url: "/comment",
       contentType: 'application/json',
       data: JSON.stringify({
           "parentId": questionId,
           "content": content,
            "type": 1
       }),
       success: function(response){
           if (response.code == 200){
               //$("#comment_section").hide();
               window.location.href='/question/'+questionId;
           } else {
               alert(response.message);
           }
           console.log(response);
       },
       dataType: "json"
   });
}

function like(e) {
    var id = $("#q_id").val();
    var userId = $("#u_id").val();
    var icon = $("#question_like");
    var likeCount = parseInt(e.getAttribute("data-like"));
    if (!icon.hasClass("active")) {
        likeCount = likeCount + 1;
        $.ajax({
            url: "/question/like/" + id + "?userId=" + userId + "&likeCount=" + likeCount,
            type: "GET",
            success:function(response){
                if (response.code == 200){
                    //$("#comment_section").hide();
                    //window.location.href='/question/'+ id;
                } else {
                    alert(response.message);
                }
            },
        });
        icon.addClass("active");
        $("#question_like").text(likeCount);
        $("#question_like").css({"color":"red","border-color": "#ccc"});
    } else {
        icon.removeClass("active");
        $.ajax({
            url: "/question/like/" + id + "?userId=" + userId + "&likeCount=" + likeCount,
            type: "GET",
        });
        $("#question_like").text(likeCount);
        $("#question_like").css({"color":"#999"});
    }
    console.log(id);
    console.log(userId);
    console.log(likeCount);
}

$("#question_like").click(function () {
    console.log("test");

});


/**
 * 二级评论
 * @param e
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    if(!comments.hasClass("in")){
        comments.addClass("in");
        e.classList.add("active");
    }else {
        comments.removeClass("in");
        e.classList.remove("active");
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + '，' + value)
        } else {
            $("#tag").val(value);
        }
    }
}

function showTag() {
    $("#show-tag").show();
}

function updateQuestion(e) {
    var id = e.getAttribute("data-id");
    var title = e.getAttribute("data-qTitle");
    var description = e.getAttribute("data-qDescription");
    var tag = e.getAttribute("data-qTag");
    $("#updateQuestion").modal({
        backdrop:"static"
    });
    $("#updateQuestion input[name=id]").val(id);
    $("#updateQuestion input[name=title]").val(title);
    $("#updateQuestion input[name=description]").val(description);
    $("#updateQuestion input[name=tag]").val(tag);
}

//查询Question信息
// function getQuestion(id) {
//     $.ajax({
//         url:"/question/"+id,
//         type:"GET",
//         success:function (result) {
//             var questionData = result.extend.question;
//             var questionId = questionData.questionId;
//             console.log(questionId);
//             $("#updateQuestion input[name=id]").val(questionId);
//             $("#updateQuestion input[name=title]").val([questionData.title]);
//             $("#nameUpdate").val(questionData.name);
//         }
//     })
// }
