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
import java.util.List;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
@Data
@TableName("book_week")
public class BookWeekEntity implements Serializable {
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
     * 星期
     */
    private Long week;
    /**
     * 星期描述
     */
    @TableField(exist = false)
    private String weekDesc;
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

    /**
     * 时间片段
     */
    @TableField(exist = false)
    private List<BookWeekTimeSliceEntity> slices;

}
