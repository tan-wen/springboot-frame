package com.aoyang.modules.book.entity;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.book.service.ExeStatus;
import com.aoyang.modules.book.service.PayStatus;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 预约申请单表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
@Data
@TableName("book_application")
public class BookApplicationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 就诊id
     */
    private Long treatmentId;
    /**
     * 预约记录id
     */
    private Long bookRecordId;
    /**
     * 缴费状态
     */
    private String payStatus = PayStatus.TB.name();
    /**
     * 执行状态
     */
    private String exeStatus = ExeStatus.V.name();


    /**
     * 病历号
     */

    private String medicareNo;

    ;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 医保号
     */
    private String insuranceNo;
    /**
     * 科室名
     */
    private String ward;
    /**
     * 就诊号
     */
    private String regNo;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 患者性别
     */
    private String sex;
    /**
     * 床号
     */
    private String bedNo;
    /**
     * 患者出生日期
     */
    private String dob;
    /**
     * 患者年龄
     */
    private String age;
    /**
     * 转入科室
     */
    private String inLoc;
    /**
     * 开单医生
     */
    private String appDoc;
    /**
     * 接收科室
     */
    private String recLoc;

    /**
     * 申请单号
     */

    private String appNo;

    /**
     * 申请日期
     */
    private Date appDate;
    /**
     * 病人表RowId
     */
    private String inPatientNo;
    /**
     * 电话
     */
    private String telNo;
    /**
     * 住址
     */
    private String address;
    /**
     * 基础医嘱项目ID
     */
    private String ordId;
    /**
     * 检查项目名
     */
    private String ordName;
    /**
     * 检查费用
     */
    private String price;
    /**
     * 有无模板
     */
    private String perTempl;
    /**
     * 医嘱ID
     */
    private String oeOrditemId;
    /**
     * 是否紧急
     */
    private String ungent;
    /**
     * 现状
     */
    private String patientNow;
    /**
     * 主诊断
     */
    private String mainDiagose;
    /**
     * 执行项目
     */
    private String purpose;
    /**
     * 内容一
     */
    private String content1;
    /**
     * 内容二
     */
    private String content2;
    /**
     * 内容三
     */
    private String content3;
    /**
     * 内容四
     */
    private String content4;
    /**
     * 内容五
     */
    private String content5;
    /**
     * 内容六
     */
    private String content6;
    /**
     * 内容七
     */
    private String content7;
    /**
     * 预约日期
     */
    private String hopeDate;
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


    /**
     * Date类型时间转为LocalDate类(yyyy-mm-dd)
     */
    @TableField(exist = false)
    private LocalDate appDateTf;


    /**
     * 关联的预约记录
     */
    @TableField(exist = false)
    private BookRecordEntity bookRecordEntity;


    /**
     * appData 关联的医技科室信息
     */
    @TableField(exist = false)
    private BaseDetectionDepEntity baseDetectionDepEntity;


    @TableField(exist = false)
    private String detectionDepName;


    /**
     * 预约时间
     */
    @TableField(exist = false)
    private String detectionTime;


    @TableField(exist = false)
    private String startTime;


    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String bookItemName;

}
