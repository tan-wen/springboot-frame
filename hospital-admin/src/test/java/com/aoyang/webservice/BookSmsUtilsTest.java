package com.aoyang.webservice;

import com.aoyang.modules.book.util.BookSmsUtils;
import org.junit.Test;

public class BookSmsUtilsTest {

    /**
     * 医技预约短信测试
     */
    @Test
    public void testSendMessage2() {
        String phoneNumber = "17712917693";
        String name = "陈艺杰";
        String project = "b超";
        BookSmsUtils.sendBookSuccessMsg(phoneNumber, name, project);
    }

    @Test
    public void testSendMessage3() {
        String phoneNumber = "17712917693";
        String name = "陈艺杰";
        String project = "b超";
        String time="2019年9月";
        BookSmsUtils.sendCancelMsg(phoneNumber, name,time, project);
    }

    @Test
    public void testSendMessage4() {
        String phoneNumber = "17712917693";
        String name = "陈艺杰";
        String time="2019年9月";
        String location="张家港塘市澳洋广场";
        String department="放射科";
        String project = "b超";
        BookSmsUtils.sendRemindPayMsg(phoneNumber, name,time,location,department, project);
    }

}
