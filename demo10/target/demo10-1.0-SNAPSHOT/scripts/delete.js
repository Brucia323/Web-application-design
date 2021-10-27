/**
 * 删除话题
 * @param {*} topicid
 * @ZZZCNY
 */
function deleteTopic(topicid) {
    const select = confirm("确定要删除吗？");
    if (select) {
        $.get("DeleteServlet", { topicid: topicid }, function () {
            $("#" + topicid).remove();
        });
    }
}
