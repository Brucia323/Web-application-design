$(document).ready(function () {
    var userid = getID();
    if (userid=="0") {
        $(location).attr("href", "index.html");
    }
});