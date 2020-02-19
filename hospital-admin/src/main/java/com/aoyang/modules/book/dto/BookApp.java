package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *申请单信息
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2020-1-20  10:06:14
 */

public class BookApp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
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
    private String payStatus;
    /**
     * 执行状态
     */
    private String exeStatus ;


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
     * 医嘱项目ID
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
     * 关联的预约记录
     */
    private BookRecord bookRecord;


    /**
     * appData 关联的医技科室信息
     */
    private DetectionDep detectionDep;


    private String detectionDepName;


    /**
     * 预约时间
     */
    private String detectionTime;


    private String startTime;


    private String endTime;

    private String bookItemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Long getBookRecordId() {
        return bookRecordId;
    }

    public void setBookRecordId(Long bookRecordId) {
        this.bookRecordId = bookRecordId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(String exeStatus) {
        this.exeStatus = exeStatus;
    }

    public String getMedicareNo() {
        return medicareNo;
    }

    public void setMedicareNo(String medicareNo) {
        this.medicareNo = medicareNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInLoc() {
        return inLoc;
    }

    public void setInLoc(String inLoc) {
        this.inLoc = inLoc;
    }

    public String getAppDoc() {
        return appDoc;
    }

    public void setAppDoc(String appDoc) {
        this.appDoc = appDoc;
    }

    public String getRecLoc() {
        return recLoc;
    }

    public void setRecLoc(String recLoc) {
        this.recLoc = recLoc;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getInPatientNo() {
        return inPatientNo;
    }

    public void setInPatientNo(String inPatientNo) {
        this.inPatientNo = inPatientNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPerTempl() {
        return perTempl;
    }

    public void setPerTempl(String perTempl) {
        this.perTempl = perTempl;
    }

    public String getOeOrditemId() {
        return oeOrditemId;
    }

    public void setOeOrditemId(String oeOrditemId) {
        this.oeOrditemId = oeOrditemId;
    }

    public String getUngent() {
        return ungent;
    }

    public void setUngent(String ungent) {
        this.ungent = ungent;
    }

    public String getPatientNow() {
        return patientNow;
    }

    public void setPatientNow(String patientNow) {
        this.patientNow = patientNow;
    }

    public String getMainDiagose() {
        return mainDiagose;
    }

    public void setMainDiagose(String mainDiagose) {
        this.mainDiagose = mainDiagose;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getContent5() {
        return content5;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public String getContent6() {
        return content6;
    }

    public void setContent6(String content6) {
        this.content6 = content6;
    }

    public String getContent7() {
        return content7;
    }

    public void setContent7(String content7) {
        this.content7 = content7;
    }

    public String getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(String hopeDate) {
        this.hopeDate = hopeDate;
    }

    public BookRecord getBookRecord() {
        return bookRecord;
    }

    public void setBookRecord(BookRecord bookRecord) {
        this.bookRecord = bookRecord;
    }

    public DetectionDep getDetectionDep() {
        return detectionDep;
    }

    public void setDetectionDep(DetectionDep detectionDep) {
        this.detectionDep = detectionDep;
    }

    public String getDetectionDepName() {
        return detectionDepName;
    }

    public void setDetectionDepName(String detectionDepName) {
        this.detectionDepName = detectionDepName;
    }

    public String getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(String detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBookItemName() {
        return bookItemName;
    }

    public void setBookItemName(String bookItemName) {
        this.bookItemName = bookItemName;
    }
}
