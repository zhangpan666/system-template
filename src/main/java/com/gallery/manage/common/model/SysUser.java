package com.gallery.manage.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像
     */
    @TableField("head_img")
    private String headImg;

    /**
     * 账户类型，1-管理员，2-普通用户
     */
    @TableField("account_type")
    private Byte accountType;

    /**
     * 手机
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 性别，0-女，1-男
     */
    @TableField("gender")
    private Byte gender;

    /**
     * 微信账号
     */
    @TableField("wechat_account")
    private String wechatAccount;

    /**
     * 微信唯一标识
     */
    @TableField("wechat_union_id")
    private String wechatUnionId;

    /**
     * 微信昵称
     */
    @TableField("wechat_nickname")
    private String wechatNickname;

    /**
     * 登录ip
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 注册ip
     */
    @TableField("register_ip")
    private String registerIp;

    /**
     * 设备id
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 上次登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 最近登录时间
     */
    @TableField("recent_login_time")
    private Date recentLoginTime;

    /**
     * 手机型号
     */
    @TableField("mobile_model")
    private String mobileModel;

    /**
     * 邮箱
     */
    @TableField("e_mail")
    private String eMail;

    /**
     * 设备类型，1-安卓，2-ios，3-其他
     */
    @TableField("os_type")
    private Byte osType;

    /**
     * 账户状态，0-不可用，1-可用
     */
    @TableField("status")
    private Byte status;


    @TableField("allowed_recharge_amount")
    private BigDecimal allowedRechargeAmount;


    @TableField("agent_register_from")
    private String agentRegisterFrom;


    @TableField("login_verify_code")
    private String loginVerifyCode;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private Long roleId;

    @TableField(exist = false)
    private String verifyCode;

    @TableField(exist = false)
    private Byte online;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
