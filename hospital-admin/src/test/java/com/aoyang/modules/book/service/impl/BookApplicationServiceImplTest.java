package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.service.BookApplicationService;
import com.google.gson.Gson;
import io.renren.AdminApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class BookApplicationServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRecordServiceImplTest.class);

    @Autowired
    private BookApplicationService bookApplicationService;

    @Test
    public void getMemberById() {
        BaseMemberEntity baseMeber = bookApplicationService.getMemberById(1L);
        LOGGER.info("BookApplicationServiceImplTest.getMemberById:{}", new Gson().toJson(baseMeber));
    }

    @Test
    public void getOutpatientAndUnpaid() {
        List<BookApplicationEntity> outpatientAndUnpaid = bookApplicationService.getOutpatientAndUnpaid();
        LOGGER.info("getOutpatientAndUnpaid:{}", new Gson().toJson(outpatientAndUnpaid));
    }

    @Test
    public void getOutpatientAndVerified() {
        List<BookApplicationEntity> apps = bookApplicationService.getOutpatientAndVerified();
        LOGGER.info("getOutpatientAndVerified:{}", new Gson().toJson(apps));
    }

    @Test
    public void getOutpatientVerifiedAndPaid() {
        List<BookApplicationEntity> app = bookApplicationService.getOutpatientVerifiedAndPaid();
        LOGGER.info("getOutpatientVerifiedAndPaid:{}", new Gson().toJson(app));
    }
}