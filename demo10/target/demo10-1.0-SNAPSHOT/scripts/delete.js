/**
 * 删除话题
 * @param {*} topicid
 * @ZZZCNY
 */
function deleteTopic(topicid) {
    var select = confirm("确定要删除吗？");
    if (select) {
            $.get("DeleteServlet", { topicid: topicid }, function () {
        $("#" + topicid).remove();
    });
    }
}