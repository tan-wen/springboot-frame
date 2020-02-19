package com.aoyang.modules.book.entity;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
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
 * @date 2019-10-14 13:34:48
 */
@Data
@TableName("book_item")
public class BookItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 执行科室
     */
    private Long detectionDepId;
    /**
     * 执行科室名称
     */
    @TableField(exist = false)
    private String detectionDepName;
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

    @TableField(exist = false)
    private List<BookItemWeekEntity> itemWeeks;

    @TableField(exist = false)
    private BaseDetectionDepEntity baseDetectionDepEntity;

}
