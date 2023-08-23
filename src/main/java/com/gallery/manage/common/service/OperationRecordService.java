package com.gallery.manage.common.service;

import com.gallery.manage.common.model.OperationRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-06-16
 */
public interface OperationRecordService extends IService<OperationRecord> {

    List<OperationRecord> query(OperationRecord operationRecord);

}
