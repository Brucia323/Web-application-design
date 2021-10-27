let title;
let content;
let topicid;

/**
 * 获取话题ID、标题、内容
 * @ZZZCNY
 */
$(document).ready(function () {
    title = localStorage.title;
    content = localStorage.content;
    topicid = localStorage.topicid;
    $("#title").val(title);
    $("#content").val(content);
});

/**
 * 发布
 * @ZZZCNY
 */
function release() {
    title = $("#title").val();
    content = $("#content").val();
    $.get("EditServlet", { topicid: topicid, title: title, content: content }, function () {
        $(location).attr("href", "index.html");
    });
}
