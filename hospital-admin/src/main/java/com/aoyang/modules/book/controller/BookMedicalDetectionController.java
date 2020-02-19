package com.aoyang.modules.book.controller;

import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.aoyang.modules.base.service.BaseOrderService;
import com.aoyang.modules.book.dto.BookApp;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.service.BookItemService;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.aoyang.modules.book.webservice.PatientDetailWs;
import com.aoyang.modules.book.webservice.inpatient.Inpatient;
import com.aoyang.modules.book.webservice.inpatient.InpatientWs;
import com.aoyang.modules.book.webservice.outpatient.Outpatient;
import com.aoyang.modules.book.webservice.outpatient.OutpatientWs;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.Constant;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.R;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 医技科室预约
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2020-01-14 13:34:48
 */
@RestController
@RequestMapping("book/medicalDetection")
public class BookMedicalDetectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookMedicalDetectionController.class);

    @Autowired
    private BaseOrderService baseOrderService;

    /**
     * 查询待预约病人清单
     */
    @RequestMapping("/list")
    public R list(@RequestParam("startDate") String startDate,
                  @RequestParam("keywords") String keywords,
                  @RequestParam("treatmentCategory") String treatmentCategory,
                  @RequestParam("detectionDeptId") Long detectionDeptId) {
        String userId = ShiroUtils.getUserEntity().getUsername();
        Long id = ShiroUtils.getUserEntity().getUserId();
        if (id == Constant.SUPER_ADMIN) {
            userId = "";
        }
        if (StringUtils.isBlank(startDate)) {
            startDate = DateUtil.getCurrentDateString();
        }

        if (StringUtils.isBlank(keywords)) {
            keywords = "";
        }
        List<String> hisOrderIds = baseOrderService.getHisOrdId(detectionDeptId);
        LOGGER.info("【医技科室】获取医技科室id：{}的关注的基础医嘱项：{}", detectionDeptId, new Gson().toJson(hisOrderIds));
        if (TreatmentCategory.OUTPATIENT.name().equals(treatmentCategory)) {
            List<Outpatient> allOutpatients = OutpatientWs.getPatientList("", startDate, keywords);
            List<Outpatient> patientList = Lists.newArrayList();
            for (Outpatient outpatient : allOutpatients) {
                List<BookApp> apps = PatientDetailWs.getPatientDetail(outpatient.getAdmNo());
                for (BookApp app : apps) {
                    if (hisOrderIds.contains(app.getOrdId())) {
                        patientList.add(outpatient);
                        break;
                    }
                }
            }
            return R.ok().put("patientList", patientList).put("userId", userId);
        } else {
            List<Inpatient> allInpatients = InpatientWs.getInPatientList("", startDate, keywords);
            List<Inpatient> patientList = Lists.newArrayList();
            for (Inpatient inpatient : allInpatients) {
                List<BookApp> apps = PatientDetailWs.getPatientDetail(inpatient.getAdmNo());
                for (BookApp app : apps) {
                    if (hisOrderIds.contains(app.getOrdId())) {
                        patientList.add(inpatient);
                        break;
                    }
                }
            }
            return R.ok().put("patientList", patientList).put("userId", userId);
        }
    }

}