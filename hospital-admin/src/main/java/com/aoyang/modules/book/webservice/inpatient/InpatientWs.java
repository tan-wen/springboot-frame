package com.aoyang.modules.book.webservice.inpatient;

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


public class InpatientWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(InpatientWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM_USER_REGNO = "?userRegno?";

    private static final String PARAM_STT_DATE = "?sttDate?";

    private static final String PARAM_KEYWORDS = "?keywords?";

    private static final String SOAP_WITH_KEYWORDS= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getIPatientList>" +
            "         <tem:UserRegno>" + PARAM_USER_REGNO + "</tem:UserRegno>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "         <tem:KeyWords>" + PARAM_KEYWORDS + "</tem:KeyWords>" +
            "      </tem:getIPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    private static final String SOAP_WITHOUT_KEYWORDS = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getIPatientList>" +
            "         <tem:UserRegno>" + PARAM_USER_REGNO + "</tem:UserRegno>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "      </tem:getIPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String SOAP_WITHOUT_NAME_KEYWORDS= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getIPatientList>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "      </tem:getIPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    private static final String SOAP_WITHOUT_NAME = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getIPatientList>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "         <tem:KeyWords>" + PARAM_KEYWORDS + "</tem:KeyWords>" +
            "      </tem:getIPatientList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";



    /**
     * 获取住院医生ID和日期获取就诊病人信息
     * @param userRegno
     * @param sttDate
     * @param keywords
     * @return
     */
    public static List<Inpatient> getInPatientList(String userRegno, String sttDate, String keywords) {
        String soap = "";
        if(StringUtils.isBlank(userRegno)){
            if (StringUtils.isBlank(keywords)) {
                soap = SOAP_WITHOUT_NAME_KEYWORDS.replace(PARAM_STT_DATE, sttDate);
            } else {
                soap = SOAP_WITHOUT_NAME.replace(PARAM_STT_DATE, sttDate).replace(PARAM_KEYWORDS, keywords);
            }
        }else {
            if (StringUtils.isBlank(keywords)) {
                soap = SOAP_WITHOUT_KEYWORDS.replace(PARAM_USER_REGNO, userRegno).replace(PARAM_STT_DATE, sttDate);
            } else {
                soap = SOAP_WITH_KEYWORDS.replace(PARAM_USER_REGNO, userRegno).replace(PARAM_STT_DATE, sttDate).replace(PARAM_KEYWORDS, keywords);
            }
        }
        List<Inpatient> inPatients = Lists.newArrayList();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("获取住院医生{}待预约病患接口，返回数据：{}", userRegno,new Gson().toJson(result));
            if (result.getCode() == 200) {
                inPatients = parseInPatient(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取住院医生待预约病患信息时发生异常", e);
        }
        return inPatients;
    }

    private static List<Inpatient> parseInPatient(String content) throws DocumentException {
        List<Inpatient> patients = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getIPatientListResponse").element("getIPatientListResult").getText());

        Element content1 = responseXml.getRootElement().element("content");
        if(null!=content1){
            for (Object element : content1.elements()) {
                Element data = ((Element) element);
                Inpatient patient = new Inpatient();
                patient.setName(data.element("pat_Name").getTextTrim());
                patient.setType(data.element("pat_Type").getTextTrim());
                patient.setSex(data.element("pat_Sex").getTextTrim());
                patient.setBirthday(data.element("pat_Birth").getTextTrim());
                patient.setNo(data.element("pat_No").getTextTrim());
                patient.setAdmNo(data.element("adm_No").getTextTrim());
                patient.setPhone(data.element("pat_phone") == null ? "" : data.element("pat_phone").getTextTrim());
                patient.setChargeType(data.element("charge_Type").getTextTrim());
                patient.setCtLocId(data.element("ctLoc_ID").getTextTrim());
                patient.setCtLocDesc(data.element("ctLoc_Desc").getTextTrim());
                patient.setWardId(data.element("ward_ID").getTextTrim());
                patient.setWardDesc(data.element("ward_Desc").getTextTrim());
                patients.add(patient);

            }
        }
        LOGGER.info("获取住院医生待预约患者列表：{}", new Gson().toJson(patients));
        return patients;
    }
}
