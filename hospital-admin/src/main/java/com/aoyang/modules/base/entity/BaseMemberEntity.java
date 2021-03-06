package com.aoyang.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-08 09:58:35
 */
@Data
@TableName("base_member")
public class BaseMemberEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 病人登记号
     */
    private String patNo;

    /**
     * 数据字典id-sex
     */
    private Long gender;

    /**
     * 性别描述
     */
    @TableField(exist = false)
    private String genderDesc;

    /**
     * 性别描述
     */
    @TableField(exist = false)
    private String age;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;
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
     * 删除标识
     */
    @TableLogic
    @JsonIgnore
    @TableField
    private Boolean deleted = false;



    @TableField(exist = false)
    private LocalDate birth;


}
