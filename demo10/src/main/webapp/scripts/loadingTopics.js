$(document).ready(function () {
    $.get("ViewTopicServlet", {}, function (data) {
        var $main = $("main");
        $main.empty();
        var json = JSON.parse(data);
        for (var index = 0; index < json.length; index++) {
            if (getID() == 0) {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '</div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button></div>');
            } else {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '</div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button><div class="comments"></div></div>');
                setTopicID(json[index].topicid);
                getComments(json[index].topicid);
                setTopicid(json[index].topicid);
            }
        }
    });
    function getComments(topicid) {
        $.get("ViewReplyServlet", { topicid }, function (data1) {
            var $comments = $("#" + topicid + " .comments");
            var json1 = JSON.parse(data1);
            for (var i = 0; i < json1.length; i++) {
                $comments.append('<div id="' + json1[i].replyid + '"><div class="rusername">' + json1[i].username + ' 回复 ' + json1[i].replyName + '</div><div class="rtime">' + json1[i].time + '</div><div class="rreply">' + json1[i].reply + '</div><fast-button class="rlikes">👍' + json1[i].likes + '</fast-button><fast-button class="rreplyNum">💬' + json1[i].replyNum + '</fast-button></div>');
                $comments.append('<div><fast-text-field class="comment">评论</fast-text-field><fast-button class="rrelease">发布</fast-button></div>');
            }
            $comments.append('<div id="c' + topicid + '"><fast-text-field class="comment">评论</fast-text-field><fast-button class="release">发布</fast-button></div>');
            $comments.hide();
        });
    }
});