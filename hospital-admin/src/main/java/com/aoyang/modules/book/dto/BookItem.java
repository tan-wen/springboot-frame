package com.aoyang.modules.book.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BookItem  implements Serializable {

    private Long bookItemId;

    private String bookItemName;

    private List<TimeSlice> timeSlices;

    private List<QuotaCategory> quotaCategories;

    private Map<String, Integer> map;

    private Map<String, Long> idMap;

    private Map<String, Integer> occupiedMap;

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public String getBookItemName() {
        return bookItemName;
    }

    public void setBookItemName(String bookItemName) {
        this.bookItemName = bookItemName;
    }

    public List<TimeSlice> getTimeSlices() {
        return timeSlices;
    }

    public void setTimeSlices(List<TimeSlice> timeSlices) {
        this.timeSlices = timeSlices;
    }

    public List<QuotaCategory> getQuotaCategories() { return quotaCategories; }

    public void setQuotaCategories(List<QuotaCategory> quotaCategories) { this.quotaCategories = quotaCategories; }

    public Map<String, Integer> getMap() { return map; }

    public void setMap(Map<String, Integer> map) { this.map = map; }

    public Map<String, Long> getIdMap() { return idMap; }

    public void setIdMap(Map<String, Long> idMap) { this.idMap = idMap; }

    public Map<String, Integer> getOccupiedMap() {
        return occupiedMap;
    }

    public void setOccupiedMap(Map<String, Integer> occupiedMap) {
        this.occupiedMap = occupiedMap;
    }
}
