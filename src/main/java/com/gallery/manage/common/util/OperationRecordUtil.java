package com.gallery.manage.common.util;

import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.service.OperationRecordService;
import com.light.config.util.ApplicationContextUtil;
import com.light.config.util.RequestUtil;
import com.light.core.utils.HttpUtil;

import javax.servlet.http.HttpServletRequest;

public class OperationRecordUtil {

    public static void record(String relatedId, String module, String description) {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        OperationRecord operationRecord = new OperationRecord().setOperationUserId(LoginUtil.getAdminLoginUserId())
                .setDescription(description).setIp(HttpUtil.getIpAddr(request)).setModule(module).setRelatedId(relatedId);
        OperationRecordService operationRecordService = ApplicationContextUtil.getBean(OperationRecordService.class);
        operationRecordService.save(operationRecord);
    }
}
