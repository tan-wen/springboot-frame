package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.inpatient.Inpatient;
import com.aoyang.modules.book.webservice.inpatient.InpatientWs;
import com.aoyang.modules.book.webservice.outpatient.Outpatient;
import com.aoyang.modules.book.webservice.outpatient.OutpatientWs;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

public class PatientTest {

    @Test
    public void getPatientListTest() {
        List<Outpatient> patientList = OutpatientWs.getPatientList("1369", "2019-11-06", "");
        System.out.println("patientList:" + new Gson().toJson(patientList));
    }


    /**
     * 获取住院病人清单
     */
    @Test
    public void getIPatientListTest() {
        //List<InPatient> patientList = InPatientWs.getInPatientList("2065", "2019-12-19","");
        List<Inpatient> patientList = InpatientWs.getInPatientList("1369", "2019-01-02", "");
        System.out.println("根据住院医生工号查询待预约病人清单patientList::" + new Gson().toJson(patientList));
    }

}
