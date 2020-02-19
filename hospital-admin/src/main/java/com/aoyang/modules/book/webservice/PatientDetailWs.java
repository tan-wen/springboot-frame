package com.aoyang.modules.book.webservice;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.aoyang.modules.book.dto.BookApp;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.renren.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PatientDetailWs {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDetailWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM_ADM_NO = "?AdmNo?";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String SOAP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getPatientDetails>" +
            "         <tem:AdmNo>" + PARAM_ADM_NO + "</tem:AdmNo>" +
            "      </tem:getPatientDetails>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    public static List<BookApp> getPatientDetail(String admNo) {
        String soap = SOAP.replace(PARAM_ADM_NO, admNo);

        List<BookApp> bookApplications = Lists.newArrayList();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            if (result.getCode() == 200) {
                bookApplications = parsePatientDetail(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取待预约病患详请时发生异常", e);
        }
        return bookApplications;
    }


    private static List<BookApp> parsePatientDetail(String content) throws DocumentException {
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getPatientDetailsResponse").element("getPatientDetailsResult").getText());
        Element intent = responseXml.getRootElement().element("intent");
        List<BookApp> entities = Lists.newArrayList();
        if (null != intent) {
            for (Object element : intent.elements()) {
                Element appData = ((Element) element);
                BookApp entity = new BookApp();
                entity.setMedicareNo(getEleText(appData, "MedicareNo"));
                entity.setCardNo(getEleText(appData, "CardNo"));
                entity.setInsuranceNo(getEleText(appData, "InsuranceNo"));
                entity.setWard(getEleText(appData, "Ward"));
                entity.setRegNo(getEleText(appData, "RegNo"));
                entity.setName(getEleText(appData, "Name"));
                entity.setSex(getEleText(appData, "Sex"));
                entity.setBedNo(getEleText(appData, "BedNo"));
                entity.setDob(getEleText(appData, "DOB"));
                entity.setAge(getEleText(appData, "Age"));
                entity.setInLoc(getEleText(appData, "InLoc"));
                entity.setAppDoc(getEleText(appData, "AppDoc"));
                entity.setRecLoc(getEleText(appData, "RecLoc"));
                entity.setAppNo(getEleText(appData, "AppNo"));
                String appDate = getEleText(appData, "AppDate");
                if (!StringUtils.isBlank(appDate)) {
                    entity.setAppDate(DateUtils.stringToDate(appDate, DATE_FORMAT));
                }
                entity.setInPatientNo(getEleText(appData, "InPatientNo"));
                entity.setTelNo(getEleText(appData, "TelNo"));
                entity.setAddress(getEleText(appData, "address"));
                entity.setOrdId(getEleText(appData, "OrdID"));
                entity.setOrdName(getEleText(appData, "OrdName"));
                entity.setPrice(getEleText(appData, "price"));
                entity.setPerTempl(getEleText(appData, "PerTempl"));
                entity.setOeOrditemId(getEleText(appData, "OEorditemID"));
                entity.setUngent(getEleText(appData, "ungent"));
                entity.setPatientNow(getEleText(appData, "PatientNow"));
                entity.setMainDiagose(getEleText(appData, "MainDiagose"));
                entity.setPurpose(getEleText(appData, "purpose"));
                entity.setContent1(getEleText(appData, "Content_1"));
                entity.setContent2(getEleText(appData, "Content_2"));
                entity.setContent3(getEleText(appData, "Content_3"));
                entity.setContent4(getEleText(appData, "Content_4"));
                entity.setContent5(getEleText(appData, "Content_5"));
                entity.setContent6(getEleText(appData, "Content_6"));
                entity.setContent7(getEleText(appData, "Content_7"));
                entity.setHopeDate(getEleText(appData, "HopeDate"));
                entities.add(entity);
            }

        }
        LOGGER.info("获取待预约患者详情：{}", new Gson().toJson(entities));
        return entities;
    }

    /**
     *
     * @param el xml的元素
     * @param key xml元素中的节点名
     * @return
     */
    private static String getEleText(Element el, String key) {
        return StringUtils.defaultString(el.element(key).getTextTrim());
    }
}
