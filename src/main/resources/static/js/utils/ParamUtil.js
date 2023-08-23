//参数工具类
var ParamUtil = {
    isEmpty: function (arr) {
        return undefined == arr || null == arr || arr.length == 0;
    },
    isNotEmpty: function (arr) {
        return !ParamUtil.isEmpty(arr);
    },
    isPhone: function (str) {
        var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
        return reg.test(val);
    },
    isIdcard: function (str) {
        var reg = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
        return reg.test(val);
    },
    isEmail: function (val) {
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        return reg.test(val);
    },
    isNull: function (param) {
        return null == param;
    },
    isUndefined: function (param) {
        return undefined == param;
    },
    isBlank: function (param) {
        return "" == $.trim(param);
    },
    isEnbale: function (param) {
        return !this.isNull(param) && !this.isUndefined(param) && !this.isBlank(param);
    }

}