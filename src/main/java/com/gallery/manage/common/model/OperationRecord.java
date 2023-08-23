package com.gallery.manage.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_operation_record")
public class OperationRecord extends Model<OperationRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    /**
     * 关联id
     */
    @TableField("related_id")
    private String relatedId;

    @TableField("module")
    private String module;

    /**
     * 操作内容
     */
    @TableField("description")
    private String description;

    /**
     * 操作人id
     */
    @TableField("operation_user_id")
    private Long operationUserId;

    /**
     * 关联类型
     */
    @TableField("related_type")
    private Byte relatedType;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private String username;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
