package com.aoyang.modules.book.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
@Data
@TableName("book_time_slice")
public class BookTimeSliceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Time startTime;
    /**
     * 结束时间
     */
    private Time endTime;
    /**
     * 时间间隔（秒）
     */
    private Integer interval;
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
     * 删除
     */
    @TableLogic
    @JsonIgnore
    @TableField
    private Boolean deleted = false;

}
