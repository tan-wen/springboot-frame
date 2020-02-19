package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 医技科室配额
 */
public class DetectionQuota implements Serializable {

    /**
     * 医技科室ID
     */
    private Long detectionDepId;

    /**
     * 医技科室名称
     */
    private String detectionDepName;

    /**
     * 预约项目
     */
    private List<BookItem> bookItems;


    public Long getDetectionDepId() {
        return detectionDepId;
    }

    public void setDetectionDepId(Long detectionDepId) {
        this.detectionDepId = detectionDepId;
    }

    public String getDetectionDepName() {
        return detectionDepName;
    }

    public void setDetectionDepName(String detectionDepName) {
        this.detectionDepName = detectionDepName;
    }

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }
}
