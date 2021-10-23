$(document).ready(function () {
    $("#button").click(function () {
        var title = $("#title").val();
        var content = $("#content").val();
        var id = getID();
        $.get("NewTopicServlet", { id, title, content }, function (result) {
            $(location).attr("href", "index.html");
        });
    });
});