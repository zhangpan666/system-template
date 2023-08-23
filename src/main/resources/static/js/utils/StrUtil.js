//string工具类
var StrUtil = {
    isBlank: function (str) {
        return undefined == str || null == str || "" == $.trim(str);
    },
    isNotBlank: function (str) {
        return !StrUtil.isBlank(str);
    },
    isNull: function (str) {
        return undefined == str || null == str;
    },
    isNotNull: function (str) {
        return !StrUtil.isNull(str);
    },
    toFloat: function (str, tofixed) {
        parseFloat(str).toFixed(tofixed);
    },
    toFloat: function (str) {
        return parseFloat(str);
    },
    toDouble: function (str, tofixed) {
        parseDouble(str).toFixed(tofixed);
    },
    toDouble: function (str) {
        parseDouble(str);
    },
    toInt: function (str) {
        parseInt(str);
    },
    replaceAll: function (str, src, dest) {
        return str.replace(new RegExp(src, "gm"), dest);
    }
}