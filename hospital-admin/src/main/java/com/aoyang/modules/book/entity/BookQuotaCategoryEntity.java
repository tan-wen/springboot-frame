package com.aoyang.modules.book.entity;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.book.service.TreatmentCategory;
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
 * 配额类别
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 09:33:16
 */
@Data
@TableName("book_quota_category")
public class BookQuotaCategoryEntity implements Serializable {
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
     * 医技科室ID
     */
    private Long detectionDepId;

    /**
     * 医技科室
     */
    @TableField(exist = false)
    private BaseDetectionDepEntity detectionDepEntity;
    /**
     * 排序
     */
    private Integer position;
    /**
     * 自动生成
     */
    private Boolean auto = false;
    /**
     * 就诊类型
     */
    private String category = TreatmentCategory.INPATIENT.name();
    /**
     * 配额类别科室
     */
    @TableField(exist = false)
    private List<BookQuotaCategoryDepEntity> deps;
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

}
