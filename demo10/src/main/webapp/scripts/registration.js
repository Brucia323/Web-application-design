/**
 * 注册
 * @ZZZCNY
 */
$(document).ready(function () {
    $("#register-button").click(function () {
        var username = $("#registered-user-name").val();
        var password = $("#sign-up-password").val();
        if (username == "" || password == "") {
            alert("用户名或密码为空！");
        } else {
            $.post("RegisterServlet", { username, password }, function (result) {
                if (result == "注册失败，请重试！") {
                    alert(result);
                } else {
                    $(location).attr("href", "login-registration.html");
                }
            });
        }
    });
    $("#registered-user-name").blur(function () {
        $("#check-username").empty();
        var username = $("#registered-user-name").val();
        if (username != "") {
            $.get("RegisterServlet", { username }, function (result) {
                alert(result);
            });
        }
    });
});