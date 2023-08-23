//layer alert提示
var MAlertUtil = {
    // 单选确认框
    info: function (content, title, callback) {
        var btnArray = ['确认'];
        mui.confirm(content, title, btnArray, function (e) {
            if (e.index == 0) {
                callback();
            }
        });
    },
    // 提示框
    tips: function (content) {
        layer.open({
            content: content,
            skin: 'msg',
            time: 2
        });
    },
    // 对话提示框
    confirm: function (content, callback) {
        var btnArray = ['取消', '确认'];
        mui.confirm(content, "提示", btnArray, function (e) {
            if (e.index == 1) {
                callback();
            }
        });
    }
}