package com.gallery.manage.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.light.core.model.Page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class LocalPageUtil {

    public static <T> PageInfo<T> copy(PageInfo oldPageInfo) {
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(oldPageInfo));
        json.remove("list");
        PageInfo newPageInfo = JSONObject.parseObject(json.toJSONString(), PageInfo.class);
        return newPageInfo;
    }

    public static <T extends Serializable, R extends Serializable> R copyByJSON(T t, Class<R> r) {
        return t == null ? null : (R) JSONObject.parseObject(JSONObject.toJSONString(t), r);
    }
}
