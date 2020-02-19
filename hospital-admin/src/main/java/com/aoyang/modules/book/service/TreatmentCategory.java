package com.aoyang.modules.book.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就诊类型
 */
public enum TreatmentCategory {

    /**
     * 就诊类型-门诊
     */
    OUTPATIENT("门诊","OUTPATIENT"),

    /**
     * 就诊类型-住院
     */
    INPATIENT("住院","INPATIENT"),

    /**
     * 就诊类型-外院
     */
    OUTSIDE("外院","OUTSIDE"),

    /**
     * 就诊类型-总名额
     */
    SUM("总名额","SUM");

    private String desc;

    private String value;

    TreatmentCategory(String desc,String value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue(){
        return value;
    }


    public static String getDescByName(String name){
        for(TreatmentCategory treatmentCategory:TreatmentCategory.values()){
            if(name.equals(treatmentCategory.name())){
                return treatmentCategory.getDesc();
            }
        }
        return  null;
    }

    /**
     *返回集合
     */
    public static List<Map<String,String>> all(){
        List<Map<String,String>> treatmentCategoryList=new ArrayList<>();
        for (TreatmentCategory treatmentCategory : TreatmentCategory.values()) {
           Map<String,String> map=new HashMap<>();
            map.put("desc",treatmentCategory.getDesc());
            map.put("value",treatmentCategory.getValue());
            treatmentCategoryList.add(map);
        }
        return treatmentCategoryList;
    }

}
