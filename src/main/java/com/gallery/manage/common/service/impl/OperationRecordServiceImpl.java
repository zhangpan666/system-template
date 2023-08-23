package com.gallery.manage.common.service.impl;

import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.mapper.OperationRecordMapper;
import com.gallery.manage.common.service.OperationRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-06-16
 */
@Service
public class OperationRecordServiceImpl extends ServiceImpl<OperationRecordMapper, OperationRecord> implements OperationRecordService {

    @Override
    public List<OperationRecord> query(OperationRecord operationRecord) {
        return baseMapper.query(operationRecord);
    }
}
