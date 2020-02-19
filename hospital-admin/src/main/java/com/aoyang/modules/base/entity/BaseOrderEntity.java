package com.aoyang.modules.base.entity;

import com.aoyang.modules.book.entity.BookItemEntity;
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
 * his侧医嘱项信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-02 10:16:18
 */
@Data
@TableName("base_order")
public class BaseOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 所属预约项目
     */
    private Long bookItemId;
    /**
     * HIS医嘱项目id
     */
    private String hisId;
    /**
     * 名称
     */
    private String name;
    /**
     * 子分类id
     */
    private String arcicRowId;
    /**
     * 子分类
     */
    private String arcicName;
    /**
     * 大类id
     */
    private String orcatRowId;
    /**
     * 大类
     */
    private String orcatName;
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


    @TableField(exist = false)
    private BookItemEntity bookItemEntity;

}
