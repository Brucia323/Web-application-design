var httpRequest = null;
var trSrc;

function createXHR() {
    if (window.XMLHttpRequest) {
        httpRequest = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e1) {
                httpRequest = null;
            }
        }
    }
    if (!httpRequest) {
        alert("fail to create httpRequest");
    }
}

function sendRequest(url, parameter, method, handler) {
    createXHR();
    if (!httpRequest) return false;
    httpRequest.onreadystatechange = handler;
    if (method == "GET") {
        httpRequest.open(method, url + '?' + parameter, true);
        httpRequest.send(null);
    }
    if (method == "POST") {
        httpRequest.open(method, url, true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send(parameter);
    }
}

/**
 * 按下键盘时进行联想。
 */
function search() {
    var inputWord = document.getElementById('inputWord').value;//输入的内容
    var url = "searchsuggest";
    var params = 'inputWord=' + inputWord;
    sendRequest(url, params, 'POST', display);
}

/**
 * 显示联想内容。
 */
function display() {
    if (httpRequest.readyState == 4) {
        if (httpRequest.status == 200) {
            var xmlDoc = httpRequest.responseXML;
            clearDivData();
            changeDivData(xmlDoc);
        } else {
            alert("您请求的页面有异常");
        }
    }
}

/**
 * 清除div中的数据。
 */
function clearDivData() {
    var tbody = document.getElementById('wordsListTbody');
    var trs = tbody.getElementsByTagName('tr');
    for (var i = trs.length - 1; i >= 0; i--) {
        tbody.removeChild(trs[i]);
    }
}

/**
 * 修改div中的数据。
 * @param {*} xmlDoc
 */
function changeDivData(xmlDoc) {
    var words = xmlDoc.getElementsByTagName('word');
    var tbody = document.getElementById('wordsListTbody');
    for (i = 0; i < words.length; i++) {
        var newTr = document.createElement('tr');
        var newCell = document.createElement('td');
        var wordText = words[i].firstChild.data;
        var textNode = document.createTextNode(wordText);
        newCell.onmouseover = function () {
            if (trSrc) {
                trSrc.style.backgroundColor = "white";
            }
            trSrc = this;
            trSrc.style.backgroundColor = "gray";
        };
        newCell.onclick = setText;
        newCell.appendChild(textNode);
        newTr.appendChild(newCell);
        tbody.appendChild(newTr);
    }
    if (words.length > 0) {
        document.getElementById('wordsListDiv').style.visibility = 'visible';
    } else {
        document.getElementById('wordsListDiv').style.visibility = 'hidden';
    }
}

/**
 * 选择联想词，自动填充。
 */
function setText() {
    document.getElementById('inputWord').value = trSrc.firstChild.data;
    document.getElementById('wordsListDiv').style.visibility = "hidden";

}

/**
 * 设置div的位置。
 */
function setDivPosition() {
    var input = document.getElementById('inputWord');
    var listdiv = document.getElementById('wordsListDiv');
    listdiv.style.left = (input.offsetLeft) + 'px';
    listdiv.style.border = 'blue 1px solid';
    listdiv.style.top = (input.offsetTop + input.offsetHeight) + 'px';
    listdiv.style.width = input.offsetWidth + 'px';
}