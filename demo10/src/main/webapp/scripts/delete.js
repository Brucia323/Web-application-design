/**
 * 删除话题
 * @param {*} topicid
 * @ZZZCNY
 */
function deleteTopic(topicid) {
    $(document).append('<fast-dialog id="example1" class="example-dialog" aria-label="Simple modal dialog" modal="true" hidden><h2>Dialog</h2><p>This is an example dialog.</p><fast-button>Close Dialog</fast-button></fast-dialog>"');
    var select = confirm("确定要删除吗？");
    if (select) {
            $.get("DeleteServlet", { topicid: topicid }, function () {
        $("#" + topicid).remove();
    });
    }
}