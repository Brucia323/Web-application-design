// $(document).ready(function () {
//     $(".likes").click(function () {
//         alert("You have already liked this post");
//         var topicid = $(this).parent()[0].id.val();
//         var userid = getID();
//         $.get("LikesServlet", { topicid, userid }, function (data) {
//             $(this).empty();
//             $(this).append('<fast-button class="likes">👍' + data + '</fast-button>');
//         });
//     });
// });
function likes() {
    var topicid = $(this).parent().attr("id");
    var userid = getID();
    $.get("LikesServlet", { topicid, userid }, function (data) {
        $(this).empty();
        $(this).append('<fast-button class="likes">👍' + data + '</fast-button>');
    });
}