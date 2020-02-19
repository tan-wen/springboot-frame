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
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-23 15:24:16
 */
@Data
@TableName("book_item_week")
public class BookItemWeekEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     *
     */
    private Long weekId;
    /**
     *
     */
    private Long bookItemId;
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
    @TableField
    private Boolean deleted = false;

}
