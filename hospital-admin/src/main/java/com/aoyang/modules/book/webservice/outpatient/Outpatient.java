package com.aoyang.modules.book.webservice.outpatient;

import java.io.Serializable;

/**
 * 门诊病患
 */
public class Outpatient implements Serializable {

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
     * 付费类型
     */
    private String payCategory;



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

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }
}
