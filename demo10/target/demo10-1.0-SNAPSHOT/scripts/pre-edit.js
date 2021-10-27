let title;
let content;

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
