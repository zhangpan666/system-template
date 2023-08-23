var ValidateInput = {
    isBlank: function (str) {//字符串为空
        return undefined == str || null == str || $.trim(str) == '';
    },
    isNotBlank: function (str) {//字符串不为空
        return !ValidateInput.isBlank(str);
    },
    variables: {//input的可用变量名称
        "clazz": "input-ve",//校验所需class
        "type": "ve-t",//校验类型
        "len": "ve-len",//最大长度
        "field": "ve-field",//字段名称
        "precision": "ve-prec",//精度
        "require": "ve-req"//是否必需
    },
    typeEnum: {//类型枚举
        "english": "english",//英文
        "number": "number",//整数
        "email": "email",//邮箱
        "phone": "phone",//电话号码
        "idcard": "idcard",//身份证号
        "float": "float",//浮点数
        "positiveNumber": "p-number",//正整数
        "negativeNumber": "n-number",//负整数
        "nonnegativeNumber": "non-number",//非负整数
        "positiveFloat": "p-float",//正浮点数
        "negativeFloat": "n-float",//负浮点数
        "nonnegativeFloat": "non-float",//非负浮点数
        "positiveNumberAndFloat": "p-number-float",//正整数和正浮点数
        "nonnegativeNumberAndFloat": "non-number-float"//非负整数和非负浮点数
    },
    getType: function (obj) {//获取类型
        return $(obj).attr(ValidateInput.variables.type);
    },
    getField:function(obj) {//获取input变量名
        return $(obj).attr(ValidateInput.variables.field);
    },
    isNotRequire:function(obj) {
        var val = $(this).attr(ValidateInput.variables.require);
        if (undefined == val || null == val || $.trim(val) == "") {
            return false;
        }
        return "false" == val;
    },
    commit: function () {//所有带验证的input提交校验
        var result = true;
        $("." + ValidateInput.variables.clazz).each(function () {
            var val = $(this).val();
            if (undefined == val || null == val || $.trim(val) == "") {
                result = false;
                return;
            }
        });
        return result;
    },
    layuiCommit: function () {
        var result = true;

        var inputs = $("." + ValidateInput.variables.clazz);

        for (var i = 0; i < inputs.length; i++) {
            var val = inputs.eq(i).val();
            if (undefined == val || null == val || $.trim(val) == "") {

                inputs.eq(i).focus();
                inputs.eq(i).addClass("layui-form-danger");

                var field = inputs.eq(i).attr(ValidateInput.variables.field);
                if (null != field) {
                    layer.tips("请填写正确的" + field, inputs.eq(i), {tips: 1});
                }

                result = false;
                return;
            }
        }

        return result;
    },
    resize: function () {// 限制字符
        var inputs = $("." + ValidateInput.variables.clazz);
        for (var i = 0; i < inputs.length; i++) {

            var attr = inputs.eq(i).attr(ValidateInput.variables.len);
            if (undefined == attr || null == attr) {
                continue;
            }
            var size = parseInt(attr);

            inputs.eq(i).attr("maxlength", size);
        }

    },
    english: function (obj) {	// 验证 英文字母
        var val = $(obj).val();
        var reg = /^[a-zA-Z]+$/;
        return reg.test(val);
    },
    precision: function (obj) {//校验精度

        var attrPrecision = $(obj).attr(ValidateInput.variables.precision);
        if (undefined == attrPrecision || null == attrPrecision) {
            return;
        }

        var val = $(obj).val();
        //先验证是否是数字
        if (!val || isNaN(val)) {
            $(obj).val(val.substring(0, val.length - 1));
            return;
        }

        var splits = val.split(".");
        if (undefined == splits[1] || null == splits[1]) {
            splits[1] = "";
        }

        var precision = attrPrecision.split(",");
        if (splits[0].length > parseInt(precision[0])) {
            splits[0] = splits[0].substring(0, parseInt(precision[0]));
        }

        if (splits[1].length > parseInt(precision[1])) {
            splits[1] = splits[1].substring(0, parseInt(precision[1]));
        }

        splits[1] = val.indexOf(".") == -1 ? "" : "." + splits[1];

        $(obj).val(splits[0] + splits[1]);
    },
    number: function (obj) {//验证整数
        var val = $(obj).val();
        var reg = /^-?[1-9]\d*$/;
        return reg.test(val);
    },
    positiveNumber: function (obj) {//正整数
        var val = $(obj).val();
        var reg = /^[1-9]\d*$/;
        return reg.test(val);
    },
    negativeNumber: function (obj) {//负整数
        var val = $(obj).val();
        var reg = /^-[1-9]\d*$/;
        return reg.test(val);
    },
    nonnegativeNumber: function (obj) {//非负整数
        var val = $(obj).val();
        var reg = /^-?[1-9]\d*$/;
        //先验证是否是整数
        if (!reg.test(val)) {
            return false;
        }

        //验证是否>=0
        if (parseInt(val) < 0) {
            return false;
        }

        return true;
    },
    float: function (obj) {//验证浮点数
        var val = $(obj).val();
        var reg = /^(-?\d+)(\.\d+)?$/;
        return reg.test(val);
    },
    positiveFloat: function (obj) {//正浮点数
        var val = $(obj).val();
        var reg = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;
        return reg.test(val);
    },
    negativeFloat: function (obj) {//负浮点数
        var val = $(obj).val();
        var reg = /^-[1-9]\d*\.\d*|-0\.\d*[1-9]\d*$/;
        return reg.test(val);
    },
    nonnegativeFloat: function (obj) {//非负浮点数
        var val = $(obj).val();

        //先验证是否是浮点数
        var reg = /^(-?\d+)(\.\d+)?$/;
        if (!reg.test(val)) {
            return false;
        }

        //验证是否>=0
        if (parseFloat(obj) < 0) {
            return false;
        }

        return true;
    },
    positiveNumberAndFloat: function (obj) {//正整数和正浮点数
        var val = $(obj).val();
        //先验证是否是数字
        if (!val || isNaN(val)) {
            return false;
        }

        if (parseFloat(val) <= 0) {
            return false;
        }

        return true;
    },
    nonnegativeNumberAndFloat: function (obj) {//非负整数和非负浮点数
        var val = $(obj).val();

        //先验证是否是数字
        if (!val || isNaN(val)) {
            return false;
        }

        //验证是否>=0
        if (parseInt(val) < 0) {
            return false;
        }

        if (parseFloat(obj) < 0) {
            return false;
        }

        return true;
    },
    email: function (obj) {// 验证邮箱地址
        var val = $(obj).val();
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        return reg.test(val);
    },
    phone: function (obj) {// 验证电话号码 
        var val = $(obj).val();
        var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
        return reg.test(val);
    },
    idcard: function (obj) {// 验证身份证号码
        var val = $(obj).val();
        var reg = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
        return reg.test(val);
    },
    validate:function(elem) {//填写时验证
        var type = ValidateInput.getType(elem);

        //校验英文
        if (ValidateInput.typeEnum.english == type) {
            return ValidateInput.english(elem);
        }

        //校验整数
        if (ValidateInput.typeEnum.number == type) {
            return ValidateInput.number(elem);
        }

        //校验正整数
        if (ValidateInput.typeEnum.positiveNumber == type) {
            return ValidateInput.positiveNumber(elem);
        }

        //非负整数和非负浮点数
        if (ValidateInput.typeEnum.nonnegativeNumberAndFloat == type) {
            return ValidateInput.nonnegativeNumberAndFloat(elem);
        }

        return true;
    },
    completeFillValidate: function (elem) {//填写完成后验证

        var type = ValidateInput.getType(elem);
        //验证邮箱
        if (ValidateInput.typeEnum.email == type) {
            return ValidateInput.email(elem);
        }

        //验证手机号
        if (ValidateInput.typeEnum.phone == type) {
            return ValidateInput.phone(elem);
        }

        //验证身份证
        if (ValidateInput.typeEnum.idcard == type) {
            return ValidateInput.idcard(elem);
        }

        //校验正整数和正浮点数
        if (ValidateInput.typeEnum.positiveNumberAndFloat == type) {
            return ValidateInput.positiveNumberAndFloat(elem);
        }

        return true;
    }
}
$(function () {

    //初始化设置input框长度
    ValidateInput.resize();

    // 填写时验证
    $("input." + ValidateInput.variables.clazz).bind('input propertychange', function () {
        //校验数字精度
        ValidateInput.precision(this);

        if (!ValidateInput.validate(this)) {
            var val = $(this).val();
            $(this).val(val.substring(0, val.length - 1));
        }
    });
    // textarea 填写验证
    $("textarea." + ValidateInput.variables.clazz).bind('input propertychange', function () {
        var type = ValidateInput.getType(this);
        //校验英文
        if (ValidateInput.typeEnum.english == type) {
            if (!ValidateInput.english(this)) {
                $(this).val("");
            }
            return;
        }

        //校验整数
        if (ValidateInput.typeEnum.number == type) {
            if (!ValidateInput.number(this)) {
                var val = $(this).val();
                $(this).val(val.substring(0, val.length - 1));
            }
            return;
        }
    });

    // 填写完时验证
    $("input." + ValidateInput.variables.clazz).blur(function () {

        if (!ValidateInput.completeFillValidate(this) || !ValidateInput.validate(this)) {
            $(this).val("");
        }
    });

});
