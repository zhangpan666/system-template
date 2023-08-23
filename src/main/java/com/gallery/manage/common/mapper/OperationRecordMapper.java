package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.OperationRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto generate
 * @since 2020-06-16
 */
public interface OperationRecordMapper extends BaseMapper<OperationRecord> {

    List<OperationRecord>query(OperationRecord operationRecord);

}
