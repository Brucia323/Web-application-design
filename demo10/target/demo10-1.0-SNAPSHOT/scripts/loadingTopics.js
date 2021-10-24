$(document).ready(function () {
    $.get("ViewTopicServlet", {}, function (data) {
        var $main = $("main");
        $main.empty();
        var json = JSON.parse(data);
        for (var index = 0; index < json.length; index++) {
            if (getID() == 0) {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '</div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button></div>');
            } else {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '</div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button></div>');
                setTopicID(json[index].topicid);
            }
        }
    });
});