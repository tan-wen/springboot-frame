package com.aoyang.modules.book.dto;

import java.io.Serializable;

public class QuotaCategory implements Serializable {

    private Long quotaCategoryId;

    private String name;

    public Long getQuotaCategoryId() { return quotaCategoryId; }

    public void setQuotaCategoryId(Long quotaCategoryId) { this.quotaCategoryId = quotaCategoryId; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
}
