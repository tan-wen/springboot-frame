package com.aoyang.modules.base.service;

/**
 *
 *@author chenyijie
 */
public enum DeptType {

    INPATIENT_DEPT(1, "住院科室"),

    MEDICAL_TECHNOLOGY_DEPT(2, "医技科室"),

    MEDICINAL_MATERIAL_DEPT(3, "药材科室"),

    ADMINISTRATION_DEPT(4, "行政科室"),

    OUTPATIENT_CONSULTING_DEPT(5, "门诊诊室"),

    OUTPATIENT_DEPT(6, "门诊科室"),

    EMERGENCY_DEPT(7, "急诊科室"),

    NURSING_DEPT(8, "护理单元"),

    FINANCE_DEPT(9, "财务科室");


    private Integer value;
    private String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    DeptType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static DeptType getByDesc(String desc) {
        for (DeptType e : values()) {
            if (e.desc.equals(desc)){
                return e;
            }
        }
        return null;
    }
}
