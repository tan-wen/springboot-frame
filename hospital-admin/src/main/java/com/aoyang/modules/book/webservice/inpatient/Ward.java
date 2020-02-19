package com.aoyang.modules.book.webservice.inpatient;

import java.io.Serializable;

/**
 * 病区
 */
public class Ward implements Serializable {

    /**
     * 病区id
     */
    private String wardId;

    /**
     *病区名
     */
    private String wardDesc;


    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardDesc() {
        return wardDesc;
    }

    public void setWardDesc(String wardDesc) {
        this.wardDesc = wardDesc;
    }
}
