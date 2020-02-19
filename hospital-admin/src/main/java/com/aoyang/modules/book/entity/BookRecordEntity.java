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
 * 预约记录表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
@Data
@TableName("book_record")
public class BookRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 预约项目id
     */
    private Long bookItemId;
    /**
     * 预约时间
     */
    private String bookTime;
    /**
     * 工作日id
     */
    private Long weekId;
    /**
     * 时间片段id
     */
    private Long timeSliceId;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 最后修改人
     */
    private Long lastModifyBy;
    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastModifyTime;


    /**
     * 已删除
     */
    @TableLogic
    @TableField
    @JsonIgnore
    private Boolean deleted = false;


    /**
     * 关联的时间片段
     */
    @TableField(exist = false)
    private BookTimeSliceEntity bookTimeSliceEntity;

}
