var title;
var content;

/**
 * 点击编辑
 * @param {*} topicid
 */
function edit(topicid) {
    title = $("#" + topicid + " .title").prop("firstChild").nodeValue;
    content = $("#" + topicid + " .content").text();
    localStorage.title = title;
    localStorage.content = content;
    localStorage.topicid = topicid;
    $(location).attr("href", "edit.html");
}

/**
 * 获取标题
 * @returns 标题
 * @deprecated
 */
function getTitle() {
    return title;
}

/**
 * 获取内容
 * @returns 内容
 * @deprecated
 */
function getContent() {
    return content;
}