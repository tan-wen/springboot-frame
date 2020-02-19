package com.aoyang.modules.book.dto;

import java.io.Serializable;


/**
 * 预约记录
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2020-1-21 10:06:14
 */
public class BookRecord implements Serializable {


    private Long id;
    /**
     * 预约项目id
     */
    private Long bookItemId;
    /**
     * 预约时间
     */
    private String bookTime;
    /**
     * 工作日id
     */
    private Long weekId;
    /**
     * 时间片段id
     */
   /* private Long timeSliceId;*/

    private TimeSlice timeSlice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public Long getWeekId() {
        return weekId;
    }

    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }

    public TimeSlice getTimeSlice() {
        return timeSlice;
    }

    public void setTimeSlice(TimeSlice timeSlice) {
        this.timeSlice = timeSlice;
    }
}
