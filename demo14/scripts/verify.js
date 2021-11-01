function verify() {
    var email = $("#email").val();
    if (email == "aaa@163.com") {
        $("span").empty();
        $("span").append("<p>用户邮箱不可用</p>");
    } else {
        $("span").empty();
        $("span").append("<p>用户邮箱可用</p>");
    }
}