package com.gallery.manage.admin.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class AdminCommonConditionVO implements Serializable {

    private Byte platformId;

    private Date beginTime;

    private Date endTime;

    private Byte manualTradeType;
}
