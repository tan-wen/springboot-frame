package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.inpatient.AdmWardWs;
import com.aoyang.modules.book.webservice.inpatient.Inpatient;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

public class AdmWardTest {

    /**
     * 根据病区ID和查询日期获取病人信息
     */
    @Test
    public void getIPatientListTest() {
        List<Inpatient> patientList = AdmWardWs.getInPatientList("10", "2019-12-19", "陆");
        System.out.println("根据病区ID和查询日期获取病人信息清单patientList::" + new Gson().toJson(patientList));
    }

}
