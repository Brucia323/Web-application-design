function preRevise(id) {
    var name = $("#" + id + " .name").text();
    var sex = $("#" + id + " .sex").text();
    var age = $("#" + id + " .age").text();
    var weight = $("#" + id + " .weight").text();
    var hight = $("#" + id + " .hight").text();
    $("#" + id + " .name").empty();
    $("#" + id + " .name").append("<input type='text' value='" + name + "'>");
    $("#" + id + " .sex").empty();
    $("#" + id + " .sex").append("<input type='text' value='" + sex + "'>");
    $("#" + id + " .age").empty();
    $("#" + id + " .age").append("<input type='text' value='" + age + "'>");
    $("#" + id + " .weight").empty();
    $("#" + id + " .weight").append("<input type='text' value='" + weight + "'>");
    $("#" + id + " .hight").empty();
    $("#" + id + " .hight").append("<input type='text' value='" + hight + "'>");
    $("#" + id + " .operation1").empty();
    $("#" + id + " .operation1").append("<button onclick='revise(" + id + ")'>修改</button>");
}

function revise(id) {
    var name = $("#" + id + " .name input").val();
    var sex = $("#" + id + " .sex input").val();
    var age = $("#" + id + " .age input").val();
    var weight = $("#" + id + " .weight input").val();
    var hight = $("#" + id + " .hight input").val();
    $.get("ReviseServlet", { id, name, sex, age, weight, hight }, function () {
        inquire();
    });
}