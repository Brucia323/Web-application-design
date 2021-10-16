$(document).ready(function () {
    $.getJSON("scripts/shengshiXinxi.json", function (data) {
        var $chengshi = $("#chengshi");
        $.each(data, function (index, chengshiXinxi) {
            if(index==$("#shengfen").val())
            {
                var chengshiXinxilist = chengshiXinxi.split(',' && '，');
                for (var index = 0; index < chengshiXinxilist.length; index++) {
                    $chengshi.append('<fast-option value="' + chengshiXinxilist[index] + '">' + chengshiXinxilist[index] + '</fast-option>');
                }
            }
        });
    });
    $.getJSON("scripts/quxianXinxi.json", function (data) {
        var $quxian = $("#quxian");
        $.each(data, function (index, quxianXinxi) {
            if (index==$("#chengshi").val()) {
                var quxianXinxilist = quxianXinxi.split(',' && '，');
                for (let index = 0; index < quxianXinxilist.length; index++) {
                    $quxian.append('<fast-option value"' + quxianXinxilist[index] + '">' + quxianXinxilist[index] + '</fast-option>');

                }
            }
        })
    })
});
$("#chengshi").change(function () {
    alert("hello");
    $.getJSON("scripts/quxianXinxi.json", function (data) {
        var $quxian = $("#quxian");
        $("#quxian fast-option").empty();
        $.each(data, function (index, quxianXinxi) {
            if (index == $("#chengshi").val()) {
                var quxianXinxilist = quxianXinxi.split(',' && '，');
                for (let index = 0; index < quxianXinxilist.length; index++) {
                    $quxian.append('<fast-option value"' + quxianXinxilist[index] + '">' + quxianXinxilist[index] + '</fast-option>');

                }
            }
        })
    })
})