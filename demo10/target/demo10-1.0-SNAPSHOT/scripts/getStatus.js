var id = 0;
var username = "";
var administrator = false;

/**
 * 获取登录状态
 * @ZZZCNY
 */
$(document).ready(function () {
    $.get("GetSessionServlet", {}, function (data) {
        if (data != "Not logged in") {
            var $account = $("#account");
            $account.empty();
            var json = JSON.parse(data);
            id = json.id;
            username = json.username;
            administrator = json.administrator;
            $account.append('<fast-button id="exit">' + username + '</fast-button>');
            if (administrator == true) {
                const $newtopic = $("#new-topic");
                $newtopic.append('<fast-anchor href="new-topic.html">新建话题</fast-anchor>');
            }
        } else {
            loadhtml();
        }
    });
});

/**
 * 获取用户ID
 * @returns 用户ID
 * @ZZZCNY
 */
function getID() {
    return id;
}

/**
 * 获取用户名
 * @returns 用户名
 * @ZZZCNY
 */
function getUsername() {
    return username;
}

/**
 * 增加控制按钮
 * @param {*} topicid
 * @ZZZCNY
 */
function addControl(topicid) {
    if (administrator == true) {
        const $control = $("#" + topicid + " .control");
        $control.append('<fast-button class="controlSticky" onclick="sticky(' + topicid + ')">置顶</fast-button>');
        $control.append('<fast-button class="controlFiner" onclick="finer(' + topicid + ')">加精</fast-button>');
        $control.append('<fast-button class="controlDelete" onclick="edit(' + topicid + ')">编辑</fast-button>');
        $control.append('<fast-button class="controlDelete" onclick="deleteTopic(' + topicid + ')">删除</fast-button>');
    }
}
