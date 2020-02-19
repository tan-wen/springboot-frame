package com.aoyang.sms;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUtil.class);

    private static final String URL = "http://sms.aoyang.com:8080/aysms/api/sendSingleMsg";

    private static final String USER_KEY = "51a41af7c63b4ca781e4e05985b686d1";

    public static boolean sendSms(String phoneNum, Integer modelId, String... params) {
        LOGGER.info("【短信】发送...");
        Map<String, Object> paramJson = new HashMap<>();
        paramJson.put("USER_KEY", USER_KEY);
        paramJson.put("MODELID", modelId);
        //paramJson.put("phoneNumber", phoneNum);
        paramJson.put("phoneNumber", "18013299879");
        paramJson.put("params", params);
        try {
            HttpClientResult result = HttpClientUtils.postJsonStringWithoutAcceptHaeder(URL, new Gson().toJson(paramJson));
            if (result.getCode() == 200) {
                return true;
            }
            LOGGER.warn("【短信】接口返回内容：{}", result.getContent());
            return false;
        } catch (Exception e) {
            LOGGER.warn("【短信】发送时发生异常.{}", e);
        }
        return false;
    }

}
