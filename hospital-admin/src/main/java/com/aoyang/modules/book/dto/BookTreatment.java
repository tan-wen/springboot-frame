package com.aoyang.modules.book.dto;

import java.io.Serializable;

/**
 * 就诊
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2020-01-21 10:06:14
 */

public class BookTreatment implements Serializable {



    private Long id;
    /**
     * 就诊号
     */
    private String admNo;
    /**
     * 患者ID
     */
    private Long memberId;
    /**
     * 患者类型
     */
    private String treatmentCategory;
    /**
     * 患者性质
     */
    private String payCategory;

    /**
     * 病区id
     */
    private Long inpatientAreaId;

    /**
     * 开单科室id
     */
    private Long depId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdmNo() {
        return admNo;
    }

    public void setAdmNo(String admNo) {
        this.admNo = admNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTreatmentCategory() {
        return treatmentCategory;
    }

    public void setTreatmentCategory(String treatmentCategory) {
        this.treatmentCategory = treatmentCategory;
    }

    public String getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(String payCategory) {
        this.payCategory = payCategory;
    }

    public Long getInpatientAreaId() {
        return inpatientAreaId;
    }

    public void setInpatientAreaId(Long inpatientAreaId) {
        this.inpatientAreaId = inpatientAreaId;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }
}
