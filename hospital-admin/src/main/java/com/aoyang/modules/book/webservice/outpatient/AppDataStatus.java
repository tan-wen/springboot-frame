package com.aoyang.modules.book.webservice.outpatient;

import com.aoyang.modules.book.service.ExeStatus;
import com.aoyang.modules.book.service.PayStatus;

import java.io.Serializable;

public class AppDataStatus implements Serializable {

    /**
     * HIS侧医嘱id
     */
    private String oeOrdItemId;

    /**
     * 支付状态
     */
    private PayStatus payStatus;

    /**
     * 执行状态
     */
    private ExeStatus exeStatus;

    public String getOeOrdItemId() {
        return oeOrdItemId;
    }

    public void setOeOrdItemId(String oeOrdItemId) {
        this.oeOrdItemId = oeOrdItemId;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public ExeStatus getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(ExeStatus exeStatus) {
        this.exeStatus = exeStatus;
    }
}
