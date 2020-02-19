package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.PatientDetailWs;
import org.junit.Test;

public class PatientDetailTest {

    @Test
    public void getPatientDetailTest() {
        PatientDetailWs.getPatientDetail("OP0002421441");
    }
}
