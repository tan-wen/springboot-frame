package com.aoyang.modules.book.webservice.outpatient;

import com.aoyang.http.HttpClientResult;
import com.aoyang.http.HttpClientUtils;
import com.aoyang.modules.book.service.ExeStatus;
import com.aoyang.modules.book.service.PayStatus;
import com.google.gson.Gson;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取检查申请单状态
 *  包含缴费状态和执行状态
 */
public class AppDataStatusWs {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppDataStatusWs.class);

    private static final String URL = "http://192.168.255.62/dthealth/web/web.DHCMedical.MedicalService.cls";

    private static final String PARAM = "?OEorditem?";

    private static final String SEPARATOR = "\\^";

    private static final String SOAP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <tem:getOEORIBilled>\n" +
            "         <tem:OEorditemID>" + PARAM + "</tem:OEorditemID>\n" +
            "      </tem:getOEORIBilled>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    public static AppDataStatus getStatus(String oeOrdItemId) {
        // 3013055||3@3013055||4，只使用3013055||3查询缴费状态和执行状态即可。格式化oeOrdItemId
        if (oeOrdItemId.contains("@")) {
            oeOrdItemId = oeOrdItemId.split("@")[0];
        }

        String soap = SOAP.replace(PARAM, oeOrdItemId);
        AppDataStatus status = null;
        try {
            HttpClientResult result = HttpClientUtils.postSoapXml(URL, soap);
            LOGGER.info("调用查询检查申请单状态的接口，返回数据：{}", new Gson().toJson(result));
            if (result.getCode() == 200) {
                status = parseStatus(result.getContent());
            }
        } catch (Exception e) {
            LOGGER.error("获取检查申请单状态时发生异常", e);
        }
        return status;
    }

    private static AppDataStatus parseStatus(String content) throws DocumentException {
        AppDataStatus status = new AppDataStatus();
        String desc = DocumentHelper.parseText(content).getRootElement().element("Body")
                .element("getOEORIBilledResponse").element("getOEORIBilledResult").getText();
        LOGGER.info("解析得到检查申请单状态：{}", desc);
        // ex : desc = "2975061||5^P^E"
        String[] data = desc.split(SEPARATOR);
        status.setOeOrdItemId(data[0]);
        status.setPayStatus(PayStatus.valueOf(data[1]));
        status.setExeStatus(ExeStatus.valueOf(data[2]));
        return status;
    }


}
