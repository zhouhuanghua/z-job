// 弹出提示效果
window.MyAlert = function (messages, callback) {
    if ($(".ec_tip").length < 1) {
        $("body").append("<div align='center' class='ec_tip'>" + messages + "</div>");
    }
    // 字体大小
    $(".ec_tip").css("font-size", "32px");
    // 谈出效果并执行回调
    $(".ec_tip").animate({top: "0px", opacity: 0}, 2000, function () {
        $(".ec_tip").remove();
        if (callback != undefined)
            callback();
    });
}