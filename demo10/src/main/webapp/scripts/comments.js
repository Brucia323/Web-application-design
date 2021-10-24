function setTopicid(topicid) {
    $(document).ready(function () {
        $("#" + topicid + " .reply").click(function () {
            $("#" + topicid + " .comments").show();
            $("#c" + topicid + " .release").click(function () {
                var userid = getID();
                var reply = $("#" + topicid + " .comment").val();
                $.get("ReplyServlet", { topicid, userid, reply, replyid: "0" }, function (data) {
                    var $comments = $("#" + topicid + " .comments");
                    var json = JSON.parse(data);
                    $comments.append('<div id="' + json[0].replyid + '"><div class="rusername">' + json[0].username + ' 回复 ' + json[0].replyName + '</div><div class="rtime">' + json[0].time + '</div><div class="rreply">' + json[0].reply + '</div><fast-button class="rlikes">👍' + json[0].likes + '</fast-button><fast-button class="rreplyNum">💬' + json[0].replyNum + '</fast-button></div>');
                    $comments.append('<div><fast-text-field class="comment">评论</fast-text-field><fast-button class="rrelease">发布</fast-button></div>');
                });
            });
        });
    });
}