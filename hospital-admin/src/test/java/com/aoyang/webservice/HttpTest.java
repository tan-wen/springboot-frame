package com.aoyang.webservice;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.google.gson.Gson;
import org.junit.Test;

public class HttpTest {

    @Test
    public void testGet() {
        try {
            HttpClientResult result = HttpClientUtils.doGet("http://www.baidu.com");
            System.out.println(new Gson().toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() {
        String json = "{\"userIds\":[\"AY055547\"],\"agentId\":32,\"articles\":[{\"title\":\"澳洋集团生日祝福\",\"description\":\"尊敬温潭先生：光阴如水，日月如梭。又是一年时光悄然而去。在这特别的日子里，公司全体同仁给您送上生日最真挚的祝福，祝您生日快乐！阖家欢乐！公司的发展倾注了您和您家人的支持和奉献，感谢您的辛勤工作，在此向您和您的家人表示感谢！愿我们在今后的工作中和谐，同心共创美好明天。\\n2019年8月8日\",\"picurl\":\"http://weixin.aoyang.com/group1/M00/05/8D/wKgBxl1LeCCADe4-AAoVjP4jB4A736.jpg\",\"url\":\"http://weixin.aoyang.com/group1/M00/05/8D/wKgBxl1LfHuALl5BAD3RGux3_Qg612.jpg\"}]}";
        try {
            HttpClientResult result = HttpClientUtils.postJsonString("http://192.168.1.97:8080/wxapp/api/msg/news", json);
            System.out.println(new Gson().toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信发送接口测试
     */
    @Test
    public void testSendMessage() {
        String json = "{\n" +
                "    \"USER_KEY\": \"51a41af7c63b4ca781e4e05985b686d1\",\n" +
                "    \"MODELID\": 250152,\n" +
                "    \"phoneNumber\": \"17712917693\",\n" +
                "    \"params\": [\n" +
                "        \"1234\",\n" +
                "        \"12\"\n" +
                "    ]\n" +
                "}";
        HttpClientResult result = null;
        try {
            result = HttpClientUtils.postJsonStringWithoutAcceptHaeder("http://sms.aoyang.com:8080/aysms/api/sendSingleMsg", json);
            System.out.println(new Gson().toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
