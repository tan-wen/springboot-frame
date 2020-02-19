package com.aoyang.modules.book.controller;

import com.aoyang.modules.base.service.BaseOrderService;
import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.dto.BookRecordItem;
import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.service.*;
import com.aoyang.modules.book.webservice.inpatient.AdmWardWs;
import com.aoyang.modules.book.webservice.inpatient.Inpatient;
import com.aoyang.modules.book.webservice.inpatient.InpatientWs;
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
 * @author chenyijie
 */
@RestController
@RequestMapping("book/inpatient")
public class BookInpatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookInpatientController.class);

    private static final String CONNECTOR = "-";

    /**
     * 空格
     */
    private static final String SPACE = " ";


    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private BookApplicationService bookApplicationService;

    @Autowired
    private BookRecordService bookRecordService;



    @Autowired
    private BaseOrderService baseOrderService;


    @Autowired
    private BookQuotaOccupiedService bookQuotaOccupiedService;

    @Autowired
    private BookTreatmentService bookTreatmentService;


    /**
     * 查询待预约病人清单
     */
    @RequestMapping("/list")
    public R getPatientList(@RequestParam("startDate") String startDate, @RequestParam("keywords") String keywords) {
        String userId = ShiroUtils.getUserEntity().getUsername();
        Long id = ShiroUtils.getUserEntity().getUserId();
        if (id == Constant.SUPER_ADMIN) {
            userId = "";
        }
        //userId = "1369";
        if (StringUtils.isBlank(startDate)) {
            startDate = DateUtil.getCurrentDateString();
        }

        if (StringUtils.isBlank(keywords)) {
            keywords = "";
        }
        // String userId = "1369";
        //sttDate = "2019-11-07";
        List<Inpatient> patientList = InpatientWs.getInPatientList(userId, startDate, keywords);
        return R.ok().put("patientList", patientList).put("userId", userId);

    }


    /**
     * 根据病区获取病人清单
     */
    @RequestMapping("/getPatientListByWard")
    public R getPatientListByWard(@RequestParam("startDate") String startDate, @RequestParam("wardId") String wardId, @RequestParam("keywords") String keywords) {
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
        // String userId = "1385";  startDate = "2019-12-30"
        List<Inpatient> patientList = AdmWardWs.getInPatientList(wardId, startDate, keywords);
        return R.ok().put("patientList", patientList).put("userId", userId);

    }


    @RequestMapping("/quota")
    public R quota(Long detectionId, Long weekId, String checkedDate, String ordIds, String depId) {
        LOGGER.info("获取医技科室的预约情况，detectionId={},weekId={},startDate={},ordIds={},开单科室depId:{}", detectionId, weekId, checkedDate, ordIds, depId);
        String[] ordIdsOther = ordIds.split(",");
        DetectionQuota quota = bookItemService.getQuotaTemp(detectionId, weekId, ordIdsOther, TreatmentCategory.INPATIENT.name(), checkedDate, depId);
        LOGGER.info("查询到的号源信息 quota={}", new Gson().toJson(quota));
        Map<String, Object> tipMap = Maps.newHashMap();
        StringBuffer sb = new StringBuffer();

        if (null != quota) {
            return R.ok().put("quota", quota);
        } else {
            sb.append("【未配置号源】");
            tipMap.put("msg", sb.toString());
            return R.error().put("tipMap", tipMap);
        }
    }


    /**
     * 住院
     *
     * @author chenyijie
     */
    @RequestMapping("/quotaoccupied")
    public R inpatientSave(String startDate, String admNo, Long bookItemId, String bookTime, Long weekId, Long detectionId, Long timeSliceId, String payCategory, String wardId, String depId, Long quotaDetailId) {
        LOGGER.info("住院病人医技预约保存占用号源,bookItemId={},weekId={},detectionId={},timeSliceId={},startDate={},adminNo={},bookTime={},payCategory={},wardId={},depId={},quotaDetailId={}", bookItemId, weekId, detectionId, timeSliceId, startDate, admNo, bookTime, payCategory, wardId, depId, quotaDetailId);
        Map<String, Object> tipMap = Maps.newHashMap();
        StringBuffer sb = new StringBuffer();
        int errorFlag = ErrorCode.NO_ERROR_CODE.getValue();
        if (StringUtils.isBlank(admNo)) {
            errorFlag = ErrorCode.ERROR_CODE.getValue();
            sb.append("【就诊号为空】");
        }
        if (StringUtils.isBlank(bookTime)) {
            errorFlag = ErrorCode.ERROR_CODE.getValue();
            sb.append("【预约时间为空】");
        }

        //校验目前号源和当前已预约记录数目
        Integer occupiedQuotaNum = bookItemService.getOccupiedQuotaNum(weekId, bookTime, bookItemId, TreatmentCategory.INPATIENT.name(), timeSliceId);
        //根据 quotaDetailId 查询号源设置的Max值,优先从book_quota_temp表查,没有值时从book_quota_detail中查找
        Integer quotaMax = bookQuotaOccupiedService.getQuotaMax(quotaDetailId);
        if (occupiedQuotaNum >= quotaMax) {
            errorFlag = ErrorCode.ERROR_CODE.getValue();
            sb.append("【号源已满,请另选择时间段预约！】");
        }
        if (ErrorCode.ERROR_CODE.getValue() == errorFlag) {
            tipMap.put("errorCode", errorFlag);
            tipMap.put("msg", sb.toString());
            return R.error().put("tipMap", tipMap);
        }

        try {
            bookQuotaOccupiedService.save(startDate, admNo, bookItemId, bookTime, weekId, detectionId, timeSliceId, payCategory, TreatmentCategory.INPATIENT.name(), wardId, depId);
        } catch (Exception e) {
            LOGGER.error("住院病人医技预约保存占用号源，保存member、book_treatment、appData失败");
        }
        try {
            bookQuotaOccupiedService.saveBookRecord(startDate, admNo, bookItemId, bookTime, weekId, detectionId, timeSliceId);

        } catch (Exception e) {
            LOGGER.error("住院病人医技预约保存占用号源，appData和record关系绑定失败");
        }

        //查找所有的预约记录
        BookRecordDetail bookRecordDetail = bookTreatmentService.getBookRecordByAdmNo(admNo);
        List<BookRecordItem> bookRecordItemList = bookRecordService.getBookRecordItem(bookRecordDetail);

        LOGGER.info("住院bookRecordDetail:{}", new Gson().toJson(bookRecordDetail));
        //LOGGER.info("门诊病人医技预约保存占用号源，quota={}", new Gson().toJson(bookRecordEntity));
        R result = R.ok().put("tipMap", tipMap).put("bookRecordItemList", bookRecordItemList);
        LOGGER.info("住院病人医技预约返回的R值为{}", new Gson().toJson(result));
        return result;

    }


}
