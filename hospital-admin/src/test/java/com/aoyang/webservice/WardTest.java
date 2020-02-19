package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.inpatient.Ward;
import com.aoyang.modules.book.webservice.inpatient.WardWs;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

public class WardTest {

    @Test
    public void getWardListTest() {
        List<Ward> wardList = WardWs.getWardList("1385");
        System.out.println("wardList:" + new Gson().toJson(wardList));
    }
}
