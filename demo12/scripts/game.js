var number;
var count = 0;
var remember = "";
var min = 1;
var max = 100;

function load() {
    var username = localStorage.username;
    $("#username").append("欢迎" + username);
    number = Math.floor(Math.random() * 100) + 1;
}

function guess() {
    count++;
    $("#result").empty();
    var input = $("#input").val();
    $("#input").val("");
    if (input == "") {
        $("#result").append("请输入数字");
    } else {
        remember = remember + " " + input;
        if (input == number) {
            $("#result").append("恭喜你猜对了<br>");
            $("#result").append("你一共猜了" + count + "次<br>");
            $("#result").append("你的记录是" + remember);
        } else if (input > number) {
            $("#result").append("猜大了, 实际数字在" + min + "-" + input + "之间");
            max = input;
        } else {
            $("#result").append("猜小了, 实际数字在" + input + "-" + max + "之间");
            min = input;
        }
    }
}

function reset() {
    $("#result").empty();
    count = 0;
    remember = "";
    min = 1;
    max = 100;
    number = Math.floor(Math.random() * 100) + 1;
}