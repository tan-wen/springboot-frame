package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 就诊信息
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2020-1-20 09:58:35
 */

public class Treatment implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String genderDesc;

    /**
     * 性别描述
     */
    private String age;

    /**
     * 出生日期
     */
    private Date birthday;


    /**
     *格式化后的日期
     */

    private LocalDate birth;

    /**
     * 手机号码
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatNo() {
        return patNo;
    }

    public void setPatNo(String patNo) {
        this.patNo = patNo;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public String getGenderDesc() {
        return genderDesc;
    }

    public void setGenderDesc(String genderDesc) {
        this.genderDesc = genderDesc;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
