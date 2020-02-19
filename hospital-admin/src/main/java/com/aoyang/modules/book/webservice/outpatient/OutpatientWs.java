package com.aoyang.modules.book.webservice.outpatient;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OutpatientWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutpatientWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM_USER_REGNO = "?userRegno?";

    private static final String PARAM_STT_DATE = "?sttDate?";

    private static final String PARAM_KEYWORDS = "?keywords?";

    private static final String SOAP_WITH_REGNO = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getPatientList>" +
            "         <tem:UserRegno>" + PARAM_USER_REGNO + "</tem:UserRegno>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "      </tem:getPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String SOAP_WITH_KEYWORDS_REGNO = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getPatientList>" +
            "         <tem:UserRegno>" + PARAM_USER_REGNO + "</tem:UserRegno>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "         <tem:KeyWords>" + PARAM_KEYWORDS + "</tem:KeyWords>" +
            "      </tem:getPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String SOAP_WITH_KEYWORD = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getPatientList>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "         <tem:KeyWords>" + PARAM_KEYWORDS + "</tem:KeyWords>" +
            "      </tem:getPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String SOAP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getPatientList>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "      </tem:getPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    public static List<Outpatient> getPatientList(String userRegno, String sttDate, String keywords) {
        String soap;
        if (StringUtils.isBlank(userRegno) && StringUtils.isBlank(keywords)) {
            soap = SOAP.replace(PARAM_STT_DATE, sttDate);
        } else if (!StringUtils.isBlank(userRegno) && StringUtils.isBlank(keywords)) {
            soap = SOAP_WITH_REGNO
                    .replace(PARAM_USER_REGNO, userRegno)
                    .replace(PARAM_STT_DATE, sttDate);
        } else if (StringUtils.isBlank(userRegno) && !StringUtils.isBlank(keywords)) {
            soap = SOAP_WITH_KEYWORD
                    .replace(PARAM_STT_DATE, sttDate)
                    .replace(PARAM_KEYWORDS, keywords);
        } else {
            soap = SOAP_WITH_KEYWORDS_REGNO
                    .replace(PARAM_USER_REGNO, userRegno)
                    .replace(PARAM_STT_DATE, sttDate)
                    .replace(PARAM_KEYWORDS, keywords);
        }
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("调用获取门诊待预约病患接口，返回数据：{}", new Gson().toJson(result));
            if (result.getCode() == 200) {
                return parseOutpatient(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取门诊待预约病患信息时发生异常", e);
        }
        return Lists.newArrayList();
    }

    private static List<Outpatient> parseOutpatient(String content) throws DocumentException {
        List<Outpatient> patients = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getPatientListResponse").element("getPatientListResult").getText());

        Element responseContent = responseXml.getRootElement().element("content");
        if (null != responseContent) {
            for (Object element : responseContent.elements()) {
                Element data = ((Element) element);
                Outpatient patient = new Outpatient();
                patient.setName(data.element("pat_Name").getTextTrim());
                patient.setType(data.element("pat_Type").getTextTrim());
                patient.setSex(data.element("pat_Sex").getTextTrim());
                patient.setBirthday(data.element("pat_Birth").getTextTrim());
                patient.setNo(data.element("pat_No").getTextTrim());
                patient.setAdmNo(data.element("adm_No").getTextTrim());
                patient.setPhone(data.element("pat_phone") == null ? "" : data.element("pat_phone").getTextTrim());
                patient.setPayCategory(data.element("charge_Type") == null ? "" : data.element("charge_Type").getTextTrim());
                patients.add(patient);
            }
        }
        LOGGER.info("获取门诊待预约患者列表：{}", new Gson().toJson(patients));
        return patients;
    }
}
