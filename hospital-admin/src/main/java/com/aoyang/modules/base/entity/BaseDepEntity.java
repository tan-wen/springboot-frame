package com.aoyang.modules.base.entity;

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
 * his侧科室信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 11:09:48
 */
@Data
@TableName("base_dep")
public class BaseDepEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * his侧科室ID
     */
    private Integer hisId;
    /**
     * 科室代码
     */
    private String code;
    /**
     * 科室名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 科室分类
     */
    private Integer deptCategory;

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
