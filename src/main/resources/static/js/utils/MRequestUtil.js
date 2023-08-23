//手机版ajax请求封装
var MRequestUtil = {
    ajax: function (settings) {
        var layerIndex = layer.open({
            shadeClose: false,
            type: 2
        });
        $.ajax({
            url: settings.url,
            data: settings.data,
            type: settings.type,
            dataType: settings.dataType,
            success: function (data) {
                setTimeout(function () {
                    settings.success(data);
                    layer.close(layerIndex)
                }, 500);
            },
            error: function (data) {
                layer.close(layerIndex);
                layer.open({
                    content: '哎呀，网络开小差了~',
                    skin: 'msg',
                    time: 1
                });
            }
        });
    },
    post: function (url, data, callback, datatype) {
        MRequestUtil.ajax({
            url: url,
            data: data,
            type: "post",
            dataType: datatype,
            success: function (data) {
                return callback(data);
            }
        });
    },
    get: function (url, data, callback, datatype) {
        MRequestUtil.ajax({
            url: url,
            data: data,
            type: "get",
            dataType: datatype,
            success: function (data) {
                return callback(data);
            }
        });
    }
}