//ajax请求封装
var RequestUtil = {
    ajax: function (settings) {
        var layerIndex = layer.load(0, {
            shade: [0.1, '#333333'] //0.1透明度的白色背景
        });
        $.ajax({
            url: settings.url,
            data: settings.data,
            type: settings.type,
            dataType: settings.dataType,
            success: function (data) {
                settings.success(data);
                layer.close(layerIndex)
            },
            error: function (data) {
                layer.close(layerIndex);
                layer.msg('哎呀，网络开小差了~', {icon: 5});
            }
        });
    },
    post: function (url, data, callback, datatype) {
        RequestUtil.ajax({
            url: url,
            data: data,
            type: "post",
            dataType: datatype,
            success: function (data) {
                return callback(data);
            }
        });
    },
    post: function (url,callback, datatype) {
        $.post(url,callback,datatype);
    },
    get: function (url, data, callback, datatype) {
        RequestUtil.ajax({
            url: url,
            data: data,
            type: "get",
            dataType: datatype,
            success: function (data) {
                return callback(data);
            }
        });
    },
    tableNoPage: function (url, params, cols) {
        var table = layui.table;
        var laypage = layui.laypage;
        RequestUtil.post(
            url + "?" + $("#layui-search").serialize(), params,
            function (data) {
                return table.render({
                    elem: '#layui-table'
                    , id: "layui-table"
                    , limit: data.page.pageSize
                    , data: data.result
                    , height: 430
                    , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                    , cols: cols
                });
            }
        );
    },
    table: function (url, params, cols) {
        var table = layui.table;
        var laypage = layui.laypage;
        RequestUtil.post(
            url + "?" + $("#layui-search").serialize(), params,
            function (data) {
                laypage.render({
                    elem: 'layui-page'
                    , count: data.page.totalCount
                    , layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                    , limit: data.page.pageSize
                    , jump: function (obj, first) {
                        if (!first) {
                            params.pageIndex = obj.curr - 1;
                            params.pageSize = obj.limit;
                            RequestUtil.tableNoPage(url, params, cols);
                        }
                    }
                });
                return table.render({
                    elem: '#layui-table'
                    , limit: data.page.pageSize
                    , data: data.result
                    , height: 430
                    , cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                    , cols: cols
                });
            }
        );
    }
}
