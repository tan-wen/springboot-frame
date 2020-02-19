package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 医技科室配额
 */
public class DetectionQuotaOccupied implements Serializable {

    /**
     *id
     */


    private  Long id;



    /**
     *预约记录表
     */
    private BookRecord bookRecord;


    /**
     * 占用数目
     */
    private Integer occupiedNum;


    /**
     * 预约项目类别
     */
    private Long quotaCategoryId;

    /**Long
     * 预约项目
     */
    private List<BookItem> bookItems;

    public BookRecord getBookRecord() {
        return bookRecord;
    }

    public void setBookRecord(BookRecord bookRecord) {
        this.bookRecord = bookRecord;
    }

    public Integer getOccupiedNum() {
        return occupiedNum;
    }

    public void setOccupiedNum(Integer occupiedNum) {
        this.occupiedNum = occupiedNum;
    }

    public Long getQuotaCategoryId() {
        return quotaCategoryId;
    }

    public void setQuotaCategoryId(Long quotaCategoryId) {
        this.quotaCategoryId = quotaCategoryId;
    }

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }
}
