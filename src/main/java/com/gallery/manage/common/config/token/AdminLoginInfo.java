package com.gallery.manage.common.config.token;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户缓存信息
 */
@Data
@Accessors(chain = true)
public class AdminLoginInfo implements Serializable {

    private Long userId;

    private String username;

    private String nickname;

    private Long categoryId;

    private Long bgAccountNo;

    private String token;

    private Long roleId;

    private String roleCode;

    private Date loginTime;

    private Date lastLoginTime;

    private Date lastOperationTime;

    private String headImg;

    private String loginIp;

    private String lastLoginIp;

    private Serializable sessionId;

}
