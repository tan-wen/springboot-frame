package com.aoyang.modules.book.webservice.wardment;
import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 获取病区基础数据
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2020-1-16 11:33:45
 */
public class WardmentWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(WardmentWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";



    private static final String SOAP= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "   <tem:getWardment/>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    /**
     * 获取病区基础数据
     *
     * @return
     */
    public static List<Wardment> getWardmentList() {

        List<Wardment> WardmentList = Lists.newArrayList();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, SOAP);
            LOGGER.info("获取病区基础数据接口返回result{}", new Gson().toJson(result));
            if (result.getCode() == 200) {
                WardmentList = parseWardment(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取病区基础数据发生异常", e);
        }
        return WardmentList;
    }

    private static List<Wardment> parseWardment(String content) throws DocumentException {
        List<Wardment> wardmentList = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getWardmentResponse").element("getWardmentResult").getText());

        Element content1 = responseXml.getRootElement().element("addtent");
        if(null!=content1){
            for (Object element : content1.elements()) {
                Element data = ((Element) element);
                Wardment wardment = new Wardment();
                wardment.setWardId(data.element("wardID").getTextTrim());
                wardment.setWardDesc(data.element("wardDesc").getTextTrim());
                wardment.setWardCode(data.element("wardCode").getTextTrim());
                wardmentList.add(wardment);

            }
        }
        LOGGER.info("获取病区集：{}", new Gson().toJson(wardmentList));
        return wardmentList;
    }
}
