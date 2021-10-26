/**
 * 登录
 * @ZZZCNY
 */
$(document).ready(function () {
    $("#login-button").click(function () {
        var username = $("#login-username").val();
        var password = $("#login-password").val();
        if (username == "" || password == "") {
            alert("用户名或密码为空！");
        } else {
            $.post("LoginServlet", { username, password }, function (result) {
                if (result == "用户名或密码不正确") {
                    alert(result);
                } else {
                    $(location).attr("href", "index.html");
                }
            });
        }
    });
});