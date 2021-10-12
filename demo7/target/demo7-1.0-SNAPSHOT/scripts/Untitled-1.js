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

function refresh() {
    var p = document.getElementById("prov").value;
    var city = document.getElementById("city");
    if (p == "") {
        city.options.length = 0;
        city.options.add(new Option("--请选择城市--"));
    } else {
        var url = "list";
        var parameter = "proc=" + p;
        sendRequest(url, parameter, "POST", show);
    }
}

function show() {
    var city = document.getElementById("city");
    if (httpRequest.readyState == 4) {
        if (httpRequest.status == 200) {
            var citylist = httpRequest.responseText.split(",");
            var citynum = citylist.length;
            city.options.length = 0;
            for (i = 0; i < citynum; i++) {
                city.options.add(new Option(citylist[i]));
            }
        }
    }
}