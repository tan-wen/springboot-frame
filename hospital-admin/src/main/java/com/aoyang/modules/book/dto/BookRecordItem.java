package com.aoyang.modules.book.dto;

import java.io.Serializable;

/**
 * 门诊预约记录显示项
 */
public class BookRecordItem  implements Serializable {
    /**Long
     * 预约记录id
     */
    private Long bookRecordId;


    /**
     * 检查项目
     */

    private String bookItemName;


    /**
     * 预约时间
     */

    private String bookTime;


    /**
     * 患者来源
     */

    private String treatmentCategory;


    /**
     * 上报时间
     */


    private String appDate;

    /**
     * 注意事项
     */




    public Long getBookRecordId() {
        return bookRecordId;
    }

    public void setBookRecordId(Long bookRecordId) {
        this.bookRecordId = bookRecordId;
    }

    public String getBookItemName() {
        return bookItemName;
    }

    public void setBookItemName(String bookItemName) {
        this.bookItemName = bookItemName;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getTreatmentCategory() {
        return treatmentCategory;
    }

    public void setTreatmentCategory(String treatmentCategory) {
        this.treatmentCategory = treatmentCategory;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }
}
