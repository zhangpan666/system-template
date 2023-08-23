package com.gallery.manage.common.model;

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
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_picture_detail")
public class PictureDetail extends Model<PictureDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 域名ID
     */
    @TableField("host_id")
    private Long hostId;

    /**
     * 图片类型
     */
    @TableField("type")
    private Byte type;

    /**
     * 实体ID
     */
    @TableField("related_id")
    private Long relatedId;

    /**
     * 类型（）
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 全尺寸高度
     */
    @TableField("height")
    private Integer height;

    /**
     * 全尺寸宽度
     */
    @TableField("width")
    private Integer width;

    /**
     * 中尺寸宽度
     */
    @TableField("middle_width")
    private Integer middleWidth;

    /**
     * 中尺寸高度
     */
    @TableField("middle_height")
    private Integer middleHeight;

    /**
     * 中尺寸路径
     */
    @TableField("middle_file_path")
    private String middleFilePath;

    /**
     * 小尺寸路径
     */
    @TableField("small_file_path")
    private String smallFilePath;

    /**
     * 小尺寸高度
     */
    @TableField("small_height")
    private Integer smallHeight;

    /**
     * 小尺寸宽度
     */
    @TableField("small_width")
    private Integer smallWidth;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态
     */
    @TableField("status")
    private Byte status;

    /**
     * -1处理失败，0-未处理，1-处理完成,2-待压缩，3-待校验
     */
    @TableField("process")
    private Byte process;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public PictureDetail setAllFilePath(String filePath) {
        this.filePath = filePath;
        this.middleFilePath = filePath;
        this.smallFilePath = filePath;
        return this;
    }

    public PictureDetail setAllWidth(Integer width) {
        this.width = width;
        this.middleWidth = width;
        this.smallWidth = width;
        return this;
    }

    public PictureDetail setAllHeight(Integer height) {
        this.height = height;
        this.middleHeight = height;
        this.smallHeight = height;
        return this;
    }

    public PictureDetail setAll(String filePath, Integer width, Integer height) {
        return setAllFilePath(filePath).setAllWidth(width).setAllHeight(height);
    }

}
