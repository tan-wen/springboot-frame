package com.aoyang.modules.book.webservice.deptment;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.aoyang.modules.base.service.DeptType;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GetDeptmentWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetDeptmentWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String DEP_ROW_ID = "?depRowId?";

    private static final String GET_DEPTMENT = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getDeptment>" +
            "         <tem:depRowId>" + DEP_ROW_ID + "</tem:depRowId>" +
            "      </tem:getDeptment>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";

    private static final String GET_ALLDEPTMENT = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">" +
            "   <soapenv:Header/>" +
            "   <soapenv:Body>" +
            "      <tem:getDeptment>" +
            "      </tem:getDeptment>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";


    /**
     * 获取所有科室
     */
    public static List<Deptment> getAllDepartment() {
        List<Deptment> DepartmentList = new ArrayList<>();
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, GET_ALLDEPTMENT);
            LOGGER.info("调用获取科室接口返回所有科室，返回数据：{}", new Gson().toJson(result));
            if (result.getCode() == 200) {
                DepartmentList = parseDeptment(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取所有科室时发生异常", e);
        }
        return DepartmentList;
    }

    /**
     * 获取住院科室
     */
    public static List<Deptment> getInpatientDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.INPATIENT_DEPT.getValue());
        LOGGER.info("获取住院科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取医技科室
     */
    public static List<Deptment> getMedicalTechnologyDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.MEDICAL_TECHNOLOGY_DEPT.getValue());
        LOGGER.info("获取医技科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取药材科室
     */
    public static List<Deptment> getMedicinalMaterialDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.MEDICINAL_MATERIAL_DEPT.getValue());
        LOGGER.info("获取药材科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取行政科室
     */
    public static List<Deptment> getAdministrationDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.ADMINISTRATION_DEPT.getValue());
        LOGGER.info("获取行政科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取门诊诊室
     */
    public static List<Deptment> getOutpatientConsultingDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.OUTPATIENT_CONSULTING_DEPT.getValue());
        LOGGER.info("获取门诊诊室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取门诊科室
     */
    public static List<Deptment> getOutpatientDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.OUTPATIENT_DEPT.getValue());
        LOGGER.info("获取门诊科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取急诊科室
     */
    public static List<Deptment> getEmergencyDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.EMERGENCY_DEPT.getValue());
        LOGGER.info("获取急诊科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取护理单元
     */
    public static List<Deptment> getNursingDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.NURSING_DEPT.getValue());
        LOGGER.info("获取护理单元，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    /**
     * 获取财务科室
     */
    public static List<Deptment> getFinanceDepartment() {
        List<Deptment> deptMentList = getDepartment(DeptType.FINANCE_DEPT.getValue());
        LOGGER.info("获取财务科室，返回数据：{}", new Gson().toJson(deptMentList));
        return deptMentList;
    }

    //获取科室
    private static List<Deptment> getDepartment(Integer depType) {
        List<Deptment> DepartmentList = new ArrayList<>();
        String soap = GET_DEPTMENT.replace(DEP_ROW_ID, depType + "");
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("调用获取科室接口，返回数据：{}", new Gson().toJson(result));
            if (result.getCode() == 200) {
                DepartmentList = parseDeptment(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取科室时发生异常", e);
        }
        return DepartmentList;
    }

    private static List<Deptment> parseDeptment(String content) throws DocumentException {
        List<Deptment> deptMents = Lists.newArrayList();
        Document responseXml = DocumentHelper.parseText(
                DocumentHelper.parseText(content).getRootElement().element("Body")
                        .element("getDeptmentResponse").element("getDeptmentResult").getText());

        Element addtent = responseXml.getRootElement().element("addtent");
        if (null != addtent) {
            for (Object element : addtent.elements()) {
                Element data = ((Element) element);
                Deptment deptMent = new Deptment();
                deptMent.setDeptID(data.element("deptID").getTextTrim());
                deptMent.setDeptCode(data.element("deptCode").getTextTrim());
                deptMent.setDeptName(data.element("deptDesc").getTextTrim());
                deptMent.setDeptAddress(data.element("deptAddress").getTextTrim());
                deptMent.setDeptType((DeptType.getByDesc(data.element("deptType").getTextTrim())).getValue());
                deptMents.add(deptMent);
            }
        }
        return deptMents;
    }

}
