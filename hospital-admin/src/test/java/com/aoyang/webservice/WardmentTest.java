package com.aoyang.webservice;


import com.aoyang.modules.book.webservice.wardment.Wardment;
import com.aoyang.modules.book.webservice.wardment.WardmentWs;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.List;

public class WardmentTest {

    @Test
    public void getWardmentListTest() {
        List<Wardment> wardmentList = WardmentWs.getWardmentList();
        System.out.println("wardmentList:" + new Gson().toJson(wardmentList));
    }

}
