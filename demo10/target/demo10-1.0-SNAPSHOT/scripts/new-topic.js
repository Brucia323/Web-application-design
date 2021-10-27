/**
 * 新建话题
 * @ZZZCNY
 */
$(document).ready(function () {
    $("#button").click(function () {
        const title = $("#title").val();
        const content = $("#content").val();
        if (title === "") {
            alert("请输入标题");
            return;
        } else if (content === "") {
            alert("请输入内容");
            return;
        }
        const id = getID();
        $.get("NewTopicServlet", { id, title, content }, function () {
            $(location).attr("href", "index.html");
        });
    });
});
