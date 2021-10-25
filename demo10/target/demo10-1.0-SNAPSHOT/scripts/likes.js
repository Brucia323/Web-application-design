function setTopicID(topicid) {
    $("#" + topicid + " .likes").click(function () {
        var userid = getID();
        $.get("LikesServlet", { topicid, userid }, function (data) {
            $("#" + topicid + " .likes").empty();
            $("#" + topicid + " .likes").append('👍' + data);
        });
    });
}
function likes(topicid, replyid) {
    var userid = getID();
    $.get("LikesServlet", { topicid, replyid, userid }, function (data) {
        $("#" + topicid + " .comments #r" + replyid + " .rlikes").empty();
        $("#" + topicid + " .comments #r" + replyid + " .rlikes").append('👍' + data);
    });
}