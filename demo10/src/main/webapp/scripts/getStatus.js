var id = "0";
var username = "";
$(document).ready(function () {
    $.get("GetSessionServlet", {}, function (data) {
        if (data != "Not logged in") {
            var $account = $("#account");
            $account.empty();
            var json = JSON.parse(data);
            id = json.id;
            username = json.username;
            var administrator = json.administrator;
            $account.append('<fast-button id="exit">' + username + '</fast-button>');
            if (administrator == true) {
                var $newtopic = $("#new-topic");
                $newtopic.append('<fast-anchor href="new-topic.html">新建话题</fast-anchor>');
            }
        }
    });
});
function getID() {
    return id;
}
function getUsername() {
    return username;
}