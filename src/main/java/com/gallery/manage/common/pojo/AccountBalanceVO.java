package com.gallery.manage.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel(description = "账户余额信息")
@Data
@Accessors(chain = true)
public class AccountBalanceVO implements Serializable {

    @ApiModelProperty(example = "账户名称")
    private String accountName;

    @ApiModelProperty(example = "余额")
    private String balance;

}
