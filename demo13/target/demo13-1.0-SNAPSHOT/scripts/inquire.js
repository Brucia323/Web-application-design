function inquire() {
    $.get("InquireServlet", function (data) {
        $(".info").remove();
        var json = JSON.parse(data);
        for (var i = 0; i < json.length; i++) {
            var item = json[i];
            $("table").append("<tr class='info' id='" + item.id + "'></tr>");
            var $tr = $("#" + item.id);
            $tr.append("<td class='id'>" + item.id + "</td>");
            $tr.append("<td class='name'>" + item.name + "</td>");
            $tr.append("<td class='sex'>" + item.sex + "</td>");
            $tr.append("<td class='age'>" + item.age + "</td>");
            $tr.append("<td class='weight'>" + item.weight + "</td>");
            $tr.append("<td class='hight'>" + item.hight + "</td>");
            $tr.append("<td class='operation1'><button onclick='preRevise(" + item.id + ")'>修改</button></td>");
            $tr.append("<td class='operation2'><button onclick='deleteInfo(" + item.id + ")'>删除</button></td>");
        }
    });
}