package com.aoyang.webservice;

import com.aoyang.modules.book.webservice.outpatient.AppDataStatus;
import com.aoyang.modules.book.webservice.outpatient.AppDataStatusWs;
import com.google.gson.Gson;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppDataStatusWsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppDataStatusWsTest.class);

    @Test
    public void test() {
        // 3013055||3@3013055||4
        // 2975061||5
        AppDataStatus status = AppDataStatusWs.getStatus("3013055||3@3013055||4");
        LOGGER.info("AppDataStatus:{}", new Gson().toJson(status));
    }

}
