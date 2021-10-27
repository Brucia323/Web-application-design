/**
 * 点赞话题
 * @param {*} topicid
 * @ZZZCNY
 */
function setTopicID(topicid) {
    $("#" + topicid + " .likes").click(function () {
        const userid = getID();
        $.get("LikesServlet", { topicid, userid }, function (data) {
            $("#" + topicid + " .likes").empty();
            $("#" + topicid + " .likes").append('👍' + data);
        });
    });
}

/**
 * 点赞评论
 * @param {*} topicid
 * @param {*} replyid
 * @ZZZCNY
 */
function likes(topicid, replyid) {
    const userid = getID();
    $.get("LikesServlet", { topicid, replyid, userid }, function (data) {
        $("#" + topicid + " .comments #r" + replyid + " .rlikes").empty();
        $("#" + topicid + " .comments #r" + replyid + " .rlikes").append('👍' + data);
    });
}
