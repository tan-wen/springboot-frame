package com.aoyang.modules.book.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AppData支付状态(HIS侧定义)
 */
public enum PayStatus {

    /**
     * 已结算
     */
    P("P","已结算"),

    /**
     * 已停止
     */
    I("I","已停止"),

    /**
     * 未计费
     */
    TB("TB","未计费"),

    /**
     * 未结算
     */
    B("B","未结算"),

    /**
     * 红冲
     */
    R("R","红冲");

    private String value;
    private String desc;

    PayStatus(String value,String desc){
        this.value=value;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue(){
        return value;
    }


    /**
     *返回集合
     */
    public static List<Map<String,String>> all(){
        List<Map<String,String>> payStatusList=new ArrayList<>();
        for (PayStatus payStatus : PayStatus.values()) {
            Map<String,String> map=new HashMap<>();
            map.put("desc",payStatus.getDesc());
            map.put("value",payStatus.getValue());
            payStatusList.add(map);
        }
        return payStatusList;
    }


}
