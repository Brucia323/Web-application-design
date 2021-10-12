var httpRequest = null;

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
        alert("Could not create httpRequest");
    }
}

function sendRequest(url, parameter, method, handler) {
    createXHR();
    if (!httpRequest) {
        return false;
    }
    httpRequest.onreadystatechange = handler;
    if (method == "GET") {
        httpRequest.open("GET", url + "?" + parameter, true);
        httpRequest.send();
    }
    if (method == "POST") {
        httpRequest.open(method, url, true);
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpRequest.send(parameter);
    }
}