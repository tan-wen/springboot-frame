package com.aoyang.modules.book.service;

/**
 * 付费类型
 */
public enum PayCategory {

    /**
     * 付费类型-城镇医保
     */
    URBAN_MEDICAL_INSURANCE("城镇医保")

    /**
     * 付费类型-居民医保
     */
    ,RESIDENT_MEDICAL_INSURANCE("居民医保")

    /**
     * 付费类型-自费
     */
    ,SELF("自费");

    private String desc;

    PayCategory(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


    public static String getNameByDesc(String desc){
        for(PayCategory payCategory:PayCategory.values()){
            if(payCategory.getDesc().equals(desc)){
                return payCategory.name();
            }
        }
        return  null;
    }

}
