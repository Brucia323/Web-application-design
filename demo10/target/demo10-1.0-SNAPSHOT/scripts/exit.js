$(document).ready(function () {
    $("#account").hover(function () {
        $("#exit").empty();
        $("#exit").append("退出");
    }, function () {
        $("#exit").empty();
        $("#exit").append(getUsername());
    });
    $("#account").click(function () {
        $.get("ExitServlet", {}, function () {
            $(location).attr("href", "index.html");
        });
    });
});