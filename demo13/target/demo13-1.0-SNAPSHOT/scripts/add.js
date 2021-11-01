function add() {
    var name = $("#name").val();
    var sex = $("#sex").val();
    var age = $("#age").val();
    var weight = $("#weight").val();
    var hight = $("#hight").val();
    $.get("AddServlet", { name, sex, age, weight, hight }, function () {
        $("#name").val("");
        $("#sex").val("");
        $("#age").val("");
        $("#weight").val("");
        $("#hight").val("");
        inquire();
    });
}