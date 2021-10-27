/**
 * 加载话题
 * @ZZZCNY
 */
$(document).ready(function () {
    $.get("ViewTopicServlet", {}, function (data) {
        const $main = $("main");
        $main.empty();
        const json = JSON.parse(data);
        for (let index = 0; index < json.length; index++) {
            if (getID() === 0) {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '<div class="finer">精</div></div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button></div>');
                addControl(json[index].topicid);
                searchFiner(json[index].topicid, json[index].essence);
            } else {
                $main.append('<div class="topic" id="' + json[index].topicid + '"><div class="control"></div><div class="username">' + json[index].username + '</div><div class="title">' + json[index].title + '<div class="finer">精</div></div><div class="time">' + json[index].time + '</div><div class="content">' + json[index].content + '</div><fast-button class="likes">👍' + json[index].likes + '</fast-button><fast-button class="reply">💬' + json[index].reply + '</fast-button><div class="comments"></div></div>');
                setTopicID(json[index].topicid);
                getComments(json[index].topicid);
                setTopicid(json[index].topicid);
                addControl(json[index].topicid);
                searchFiner(json[index].topicid, json[index].essence);
                searchSticky(json[index].topicid, json[index].sticky);
            }
        }
    });
});

/**
 * 处理加精相关问题
 * @param {*} topicid
 * @param {*} essence
 * @ZZZCNY
 */
function searchFiner(topicid, essence) {
    $(document).ready(function () {
        if (essence === false) {
            $("#" + topicid + " .finer").hide();
        } else {
            $("#" + topicid + " .control .controlFiner").empty()
                .append("取消加精");
        }
    });
}

/**
 * 处理置顶相关问题
 * @param {*} topicid
 * @param {*} sticky
 * @ZZZCNY
 */
function searchSticky(topicid, sticky) {
    $(document).ready(function () {
        if (sticky === true) {
            $("#" + topicid + " .control .controlSticky").empty()
                .append("取消置顶");
        }
    });
}
