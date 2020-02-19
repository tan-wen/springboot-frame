package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 预约记录详情
 */
public class BookRecordDetail extends BookTreatment implements Serializable {

    /**Long
     * 申请单
     */
    private List<BookApp> bookApps;

    public List<BookApp> getBookApps() {
        return bookApps;
    }

    public void setBookApps(List<BookApp> bookApps) {
        this.bookApps = bookApps;
    }
}
