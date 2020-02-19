package com.aoyang.modules.book.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 号源配置详情临时表
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-06 14:14:08
 */
@Data
@TableName("book_quota_temp")
public class BookQuotaTempEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 预约项目ID
     */
    private Long bookItemId;
    /**
     * 工作日ID
     */
    private Long weekId;
    /**
     * 时间片段ID
     */
    private Long timeSliceId;
    /**
     * 配额类别id
     */
    private Long quotaCategoryId;
    /**
     * 最大数
     */
    private Integer max;
    /**
     * 完整时间
     */

    private String fullTime;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 最后修改者
     */
    private Long lastModifyBy;
    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;
    /**
     * 删除标识
     */
    @TableLogic
    @JsonIgnore
    private Boolean deleted = false;

}
