package com.aoyang.modules.book.webservice.wardment;

import java.io.Serializable;

/**
 * 病区基础数据
 */
public class Wardment implements Serializable {

    /**
     * 病区id
     */
    private String wardId;

    /**
     *病区名
     */
    private String wardDesc;


    /**
     *病区码
     */
    private String wardCode;



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

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }
}
