package com.gallery.manage.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class YuanPayWithdrawNotifyVO implements Serializable {

    private  String oid_partner;
    private  String money_order;
    private  String no_order;
    private  String oid_paybill;
    private  String sign;
    private  String time_order;
    private  String result_pay;
    private  String sign_type;


}
