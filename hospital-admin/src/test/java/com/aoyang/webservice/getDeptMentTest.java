package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.deptment.GetDeptmentWs;
import org.junit.Test;

public class getDeptMentTest {

    @Test
    public void getAdministrationDepartmentTest() {
        GetDeptmentWs.getAdministrationDepartment();
    }

    @Test
    public void getAllDeptMentTest() {
        GetDeptmentWs.getAllDepartment();
    }
}
