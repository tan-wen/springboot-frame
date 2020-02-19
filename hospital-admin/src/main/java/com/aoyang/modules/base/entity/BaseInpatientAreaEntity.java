package com.aoyang.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 病区
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-07 08:46:47
 */
@Data
@TableName("base_inpatient_area")
public class BaseInpatientAreaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * his侧病区ID
     */
    private String hisId;
    /**
     * 病区名字
     */
    private String name;
    /**
     * 病区描述
     */
    private String description;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改人
     */
    private Long lastModifyBy;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;


    /**
     * 删除
     */
    @TableLogic
    @JsonIgnore
    @TableField
    private Boolean deleted = false;

}
