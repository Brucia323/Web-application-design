/**
 * 置顶
 * @param {*} topicid
 * @ZZZCNY
 */
function sticky(topicid) {
    $.get("StickyServlet", { topicid: topicid }, function (data) {
        if (data === 1) {
            //置顶
            const clone = $("#" + topicid).clone();
            $("#" + topicid).remove();
            $("main").prepend(clone);
            $("#" + topicid + " .control .controlSticky").empty()
                .append("取消置顶");
        } else {
            //取消置顶
            $(location).attr("href", "index.html");
        }
    });
}
