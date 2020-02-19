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
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-12 16:52:44
 */
@Data
@TableName("base_detection_dep")
public class BaseDetectionDepEntity implements Serializable {
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
     * 位置
     */
    private String location;
    /**
     * 顺序
     */
    private Integer position;
    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 自定义科室分组
     */
    private Boolean diffDep = false;
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
