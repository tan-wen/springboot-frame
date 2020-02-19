package com.aoyang.modules.book.webservice.inpatient;
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
 * 根据医生/护士ID获取病区信息
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2019-12-19 11:33:45
 */
public class WardWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(WardWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM_USER_REGNO = "?userRegno?";

    private static final String SOAP= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getWardList>" +
            "         <tem:UserId>"+ PARAM_USER_REGNO + "</tem:UserId>" +
            "      </tem:getWardList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    /**
     * 根据医生/护士ID获取病区集
     * @param userRegno
     * @param sttDate
     * @param keywords
     * @return
     */
    public static List<Ward> getWardList(String userRegno) {
        String soap = SOAP.replace(PARAM_USER_REGNO, userRegno);
        List<Ward> wardList = Lists.newArrayList();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("根据医生/护士ID{}获取病区集调用接口返回result{}", userRegno,new Gson().toJson(result));
            if (result.getCode() == 200) {
                wardList = parseWard(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("根据医生/护士ID{}获取病区集发生异常", e);
        }
        return wardList;
    }

    private static List<Ward> parseWard(String content) throws DocumentException {
        List<Ward> wardList = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getWardListResponse").element("getWardListResult").getText());

        Element content1 = responseXml.getRootElement().element("content");
        if(null!=content1){
            for (Object element : content1.elements()) {
                Element data = ((Element) element);
                Ward ward = new Ward();
                ward.setWardId(data.element("ward_ID").getTextTrim());
                ward.setWardDesc(data.element("ward_Desc").getTextTrim());
                wardList.add(ward);

            }
        }
        LOGGER.info("根据医生/护士ID获取病区集：{}", new Gson().toJson(wardList));
        return wardList;
    }
}
