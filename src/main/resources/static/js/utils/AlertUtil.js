//layer alert提示
var AlertUtil = {
    info: function (content) {
        layer.msg(content, {icon: 1, time: 2000, resize: false});
    },
    info: function (content, callback) {
        layer.msg(content, {icon: 1, time: 2000, resize: false}, callback);
    },
    warn: function (content) {
        layer.msg(content, {icon: 0, time: 2000, resize: false});
    },
    success: function (content, time) {
        layer.msg(content, {icon: 1, time: time, resize: false});
    },
    success: function (content, time, callback) {
        layer.msg(content, {icon: 1, time: time, resize: false}, callback);
    },
    error: function (content) {
        layer.msg(content, {icon: 2, time: 2000, resize: false});
    },
    error: function (content,time) {
        layer.msg(content, {icon: 2, time: time, resize: false});
    },
    confirm: function (content, callback) {
        layer.confirm(content, {icon: 3, title: '提示'},function (index) {
            layer.close(index);
            callback();
        });
    },
    open: function (title, link) {
        layer.open({
            title: title,
            type: 2,
            closeBtn: 1,
            area: ["100%", "100%"],
            content: link
        });
    },
    alert:function (content) {
        layer.alert(content);
    },
    pictureView:function(src, size) {
    var index = layer.open({
        title: '图片预览',
        area: size,
        type: 1,
        maxmin: true,
        resize: true,
        anim: 1,
        closeBtn:1,
        content: "<img style='width: " + size[0] + ";height: " + size[1] + "' src=" + src + ">"
    });
    // layer.full(index);
    },
    open: function (title, content,offset) {
        layer.open({
            title: title,
            type: 0,
            closeBtn: 1,
            shade: 0,
            offset: offset,
            content: content
        });
    },
    open: function (title, content,offset,btnCallback) {
        layer.open({
            title: title,
            type: 0,
            closeBtn: 1,
            shade: 0,
            offset: offset,
            content: content,
            yes: function(index, layero){
                layer.close(index); //如果设定了yes回调，需进行手工关闭
                btnCallback();
            }
        });
    },
    open: function (title, content,offset,callback) {
        layer.open({
            title: title,
            type: 0,
            closeBtn: 1,
            shade: 0,
            offset: offset,
            content: content,
            cancel: callback
        });
    },
    open: function (title, content,offset,callback,btnCallback) {
        layer.open({
            title: title,
            type: 0,
            closeBtn: 1,
            shade: 0,
            offset: offset,
            content: content,
            cancel: callback,
            yes: function(index, layero){
                layer.close(index); //如果设定了yes回调，需进行手工关闭
                btnCallback();
            }
        });
    },
    open: function (title, content,offset,callback,btnArr,btn1Callback,btn2Callback) {
        layer.open({
            title: title,
            type: 0,
            closeBtn: 1,
            shade: 0,
            offset: offset,
            content: content,
            cancel: callback,
            btn: btnArr,
            yes: function(index, layero){
                layer.close(index); //如果设定了yes回调，需进行手工关闭
                btn1Callback();
            },btn2: function(){
                layer.closeAll();
                btn2Callback();
            }
        });
    }
}
