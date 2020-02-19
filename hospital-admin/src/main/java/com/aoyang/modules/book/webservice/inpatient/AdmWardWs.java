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


/**
 * 根据病区ID和查询日期获取病人信息
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2019-12-19 11:33:45
 */
public class AdmWardWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmWardWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM_WARD_ID = "?wardId?";

    private static final String PARAM_STT_DATE = "?sttDate?";

    private static final String PARAM_KEYWORDS = "?keywords?";

    private static final String SOAP_WITH_KEYWORDS= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getAdmWardList>" +
            "         <tem:WardId>" + PARAM_WARD_ID + "</tem:WardId>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "         <tem:keyWord>" + PARAM_KEYWORDS +"</tem:keyWord>" +
            "      </tem:getAdmWardList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String SOAP_WITHOUT_KEYWORDS= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getAdmWardList>" +
            "         <tem:WardId>" + PARAM_WARD_ID + "</tem:WardId>" +
            "         <tem:SttDate>" + PARAM_STT_DATE + "</tem:SttDate>" +
            "      </tem:getAdmWardList>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    /**
     * 根据病区ID和查询日期获取病人信息
     * @param WardId  病区ID
     * @param sttDate  查询日期
     * @return
     */
    public static List<Inpatient> getInPatientList(String WardId, String sttDate, String keywords) {
        String soap = "";
        if (StringUtils.isBlank(keywords)) {
            soap = SOAP_WITHOUT_KEYWORDS.replace(PARAM_WARD_ID, WardId).replace(PARAM_STT_DATE, sttDate);
        } else {
            soap = SOAP_WITH_KEYWORDS.replace(PARAM_WARD_ID, WardId).replace(PARAM_STT_DATE, sttDate).replace(PARAM_KEYWORDS, keywords);
        }

        List<Inpatient> inPatients = Lists.newArrayList();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("病区WardId{}在{}待预约病患接口，返回数据：{}", WardId,sttDate,new Gson().toJson(result));
            if (result.getCode() == 200) {
                inPatients = parseInPatient(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取病区待预约病患信息时发生异常", e);
        }
        return inPatients;
    }

    private static List<Inpatient> parseInPatient(String content) throws DocumentException {
        List<Inpatient> patients = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getAdmWardListResponse").element("getAdmWardListResult").getText());

        Element content1 = responseXml.getRootElement().element("content");
        if(null!=content1){
            for (Object element : content1.elements()) {
                Element data = ((Element) element);
                Inpatient patient = new Inpatient();
                patient.setName(data.element("pat_Name").getTextTrim());
                patient.setType(data.element("pat_Type") == null ? "" : data.element("pat_Type").getTextTrim());
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
        LOGGER.info("获取病区待预约病患列表：{}", new Gson().toJson(patients));
        return patients;
    }
}
