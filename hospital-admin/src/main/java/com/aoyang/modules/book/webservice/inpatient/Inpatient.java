package com.aoyang.modules.book.webservice.inpatient;

import java.io.Serializable;

/**
 * 住院病患
 */
public class Inpatient implements Serializable {

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 患者类型：门诊、住院等
     */
    private String type;


    /**
     * 患者性别
     */
    private String sex;

    /**
     * 患者生日
     */
    private String birthday;

    /**
     * 患者登记号
     */
    private String no;

    /**
     * 患者手机号
     */
    private String phone;

    /**
     * 患者就诊号
     */
    private String admNo;

    /**
     * 患者年龄(41岁)
     */
    private String age;


    /**
     * 费用类型
     */
    private String chargeType;


    /**
     * 所属病区病区id
     */
    private Long inpatientAreaId;

    /**
     * 所属病区病区id（his侧）
     */
    private String wardId;


    /**
     * 所属病区病区名
     */
    private String wardDesc;



    /**
     * 开单科室id
     */
    private Long depId;

    /**
     * 开单科室id（his侧）
     */
    private String ctLocId;


    /**
     * 开单科室名
     */
    private String ctLocDesc;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdmNo() {
        return admNo;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getCtLocId() {
        return ctLocId;
    }

    public void setCtLocId(String ctLocId) {
        this.ctLocId = ctLocId;
    }

    public String getCtLocDesc() {
        return ctLocDesc;
    }

    public void setCtLocDesc(String ctLocDesc) {
        this.ctLocDesc = ctLocDesc;
    }


    public String getWardDesc() {
        return wardDesc;
    }

    public void setWardDesc(String wardDesc) {
        this.wardDesc = wardDesc;
    }

    public Long getInpatientAreaId() {
        return inpatientAreaId;
    }

    public void setInpatientAreaId(Long inpatientAreaId) {
        this.inpatientAreaId = inpatientAreaId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }
}
