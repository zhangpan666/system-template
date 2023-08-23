var CommonUtil = {
    open: function (title, area, url, callback) {
        layer.open({
            // title: "<span style='font-weight: bold'>" + title + "</span>",
            title: title,
            area: area,
            type: 2,
            maxmin: true,
            resize: false,
            content: url,
            cancel: callback
        });
    },
    close: function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        window.parent.location.reload();

    },
    ajaxMultipartRequest: function (data, url, callback) {
        var layerIndex = layer.load(0, {
            shade: [0.1, '#333333'] //0.1透明度的白色背景
        });
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: false,
            data: data,
            processData: false,
            contentType: false,
            success: function (result) {
                callback(result);
                layer.close(layerIndex);
            },
            error: function (result) {
                AlertUtil.error("请求发生错误,请联系管理员！");
                layer.close(layerIndex);
            }
        })
    },
    ajaxRequest: function (data, url, callback) {
        var layerIndex = layer.load(0, {
            shade: [0.1, '#333333'] //0.1透明度的白色背景
        });
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: false,
            data: data,
            success: function (result) {
                callback(result);
                layer.close(layerIndex);
            },
            error: function (result) {
                AlertUtil.error("请求发生错误,请联系管理员！");
                layer.close(layerIndex);
            }
        })
    },
    ajaxRequestTraditional: function (data, url, callback) {
        var layerIndex = layer.load(0, {
            shade: [0.1, '#333333'] //0.1透明度的白色背景
        });
        $.ajax({
            url: url,
            type: "post",
            dataType: "json",
            async: false,
            data: data,
            traditional: true,
            success: function (result) {
                callback(result);
                layer.close(layerIndex);
            },
            error: function (result) {
                AlertUtil.error("请求发生错误,请联系管理员！");
                layer.close(layerIndex);
            }
        })
    },
    post: function (url, callback) {
        var layerIndex = layer.load(0, {
            shade: [0.1, '#333333'] //0.1透明度的白色背景
        });
        $.post(url, function (result) {
            callback(result);
            layer.close(layerIndex);
        });
    },
    isEmpty: function (obj) {
        if (obj == null || obj == undefined || obj == "") {
            return true;
        }
        return false;
    },
    queryList: function () {
        $('#pageNum').val(1);
        $("#entity_search_form").submit();
    }
}
