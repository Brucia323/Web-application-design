function setTopicID(topicid) {
    $("#" + topicid + " .likes").click(function () {
        var userid = getID();
        $.get("LikesServlet", { topicid, userid }, function (data) {
            $("#" + topicid + " .likes").empty();
            $("#" + topicid + " .likes").append('👍' + data);
        });
    });
}