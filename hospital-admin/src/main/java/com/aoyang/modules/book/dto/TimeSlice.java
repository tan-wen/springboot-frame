package com.aoyang.modules.book.dto;

import java.io.Serializable;

public class TimeSlice implements Serializable {

    private Long timeSliceId;

    private String startTime;

    private String endTime;

    public Long getTimeSliceId() { return timeSliceId; }

    public void setTimeSliceId(Long timeSliceId) { this.timeSliceId = timeSliceId; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }
}
