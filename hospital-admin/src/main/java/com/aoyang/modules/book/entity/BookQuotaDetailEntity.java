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
 * 号源配置详情表
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-19 14:11:37
 */
@Data
@TableName("book_quota_detail")
public class BookQuotaDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long bookItemId;
    /**
     *
     */
    private Long weekId;
    /**
     *
     */
    private Long timeSliceId;
    /**
     *
     */
    private Long quotaCategoryId;
    /**
     *
     */
    private Integer max;
    /**
     *
     */
    private Long createBy;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *
     */
    private Long lastModifyBy;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;
    /**
     *
     */
    @TableLogic
    @JsonIgnore
    private Boolean deleted = false;

}
