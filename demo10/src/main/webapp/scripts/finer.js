/**
 * 加精
 * @param {*} topicid
 * @ZZZCNY
 */
function finer(topicid) {
    $.get("FinerServlet", { topicid: topicid }, function (data) {
        if (data == 1) {
            //加精
            $("#" + topicid + " .finer").show();
            $("#" + topicid + " .control .controlFiner").empty()
                .append("取消加精");
        } else {
            //取消加精
            $("#" + topicid + " .finer").hide();
            $("#" + topicid + " .control .controlFiner").empty()
                .append("加精");
        }
    });
}
