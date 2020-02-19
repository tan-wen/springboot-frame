package com.aoyang.modules.book.util;

import com.aoyang.sms.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookSmsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookSmsUtils.class);

    private static final Integer MODLE_ID_APPOINTMENT_COMPLETED = 482691;

    private static final Integer MODLE_ID_PAYMENT_SUCCEED = 482696;

    private static final Integer MODLE_ID_APPOINTMENT_CANCLED = 482706;

    private static final String PHONE_NUMBER_REG = "^(1[3-9])\\d{9}$";

    /**
     * 发送提醒缴费的短信通知
     * phoneNum:发送人电话  name:发送人名字 time:时间 location:地区 department:科室 project:预约项目
     *
     * @return
     */
    public static Boolean sendRemindPayMsg(String phoneNum, String name, String time, String location, String department, String project) {
        LOGGER.info("【短信】提醒缴费:{},{},{},{},{},{}", phoneNum, name, time, location, department, project);
        if (phoneNum.matches(PHONE_NUMBER_REG)) {
            return SmsUtil.sendSms(phoneNum, MODLE_ID_PAYMENT_SUCCEED, name, time, location, department, project);
        } else {
            LOGGER.info("【短信】提醒缴费:手机号有误");
            return false;
        }
    }

    /**
     * 发送预约成功短信通知
     *
     * @return
     */
    public static Boolean sendBookSuccessMsg(String phoneNum, String name, String project) {
        LOGGER.info("【短信】预约成功:{},{},{}", phoneNum, name, project);
        if (phoneNum.matches(PHONE_NUMBER_REG)) {
            return SmsUtil.sendSms(phoneNum, MODLE_ID_APPOINTMENT_COMPLETED, name, project);
        } else {
            LOGGER.info("【短信】预约成功:手机号有误");
            return false;
        }

    }

    /**
     * 发送取消预约短信通知
     *
     * @return
     */
    public static Boolean sendCancelMsg(String phoneNum, String name, String time, String project) {
        LOGGER.info("【短信】取消预约:{},{},{},{}", phoneNum, name, time, project);
        if (phoneNum.matches(PHONE_NUMBER_REG)) {
            return SmsUtil.sendSms(phoneNum, MODLE_ID_APPOINTMENT_CANCLED, name, time, project);
        } else {
            LOGGER.info("【短信】预约成功:手机号有误");
            return false;
        }
    }
}
