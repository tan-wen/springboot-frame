package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.service.BookRecordService;
import com.google.gson.Gson;
import io.renren.AdminApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class BookRecordServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRecordServiceImplTest.class);

    @Autowired
    private BookRecordService bookRecordService;


    @Test
    public void getBookItemById() {
        BookItemEntity bookItem = bookRecordService.getBookItemById(1L);
        LOGGER.info("BookRecordServiceImplTest.getBookItemById : {}", new Gson().toJson(bookItem));
    }
}