package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.aoyang.modules.base.service.BaseMemberService;
import com.aoyang.modules.base.service.BaseOrderService;
import com.aoyang.modules.book.dto.BookApp;
import com.aoyang.modules.book.dto.BookPatientDetail;
import com.aoyang.modules.book.dto.DetectionDep;
import com.aoyang.modules.book.dto.Treatment;
import com.aoyang.modules.book.service.BookPatientDetailService;
import com.aoyang.modules.book.webservice.PatientDetailWs;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import io.renren.common.utils.DateUtil;
import io.renren.common.utils.DateUtils;
import io.renren.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("bookPatientDetailService")
public class BookPatientDetailServiceImpl implements BookPatientDetailService {


    private static final Logger LOGGER = LoggerFactory.getLogger(BookPatientDetailServiceImpl.class);


    @Autowired
    private BaseMemberService baseMemberService;

    @Autowired
    private BaseOrderService baseOrderService;

    private static final String SYS_DICT_KEY_SEX = "sex";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private SysDictService sysDictService;


    public BookPatientDetail getPatientDetail(String admNo) {
        BookPatientDetail bookPatientDetail = new BookPatientDetail();
        List<BookApp> interookApps = PatientDetailWs.getPatientDetail(admNo);
        if (!interookApps.isEmpty()) {
            List<BookApp> bookApps = Lists.newArrayList();
            Treatment treatment = new Treatment();
            for (BookApp bookApp : interookApps) {
                //Element appdata = ((Element)element);
                String patNo = bookApp.getRegNo();
                //优先从base_member中查询，没有结果则从接口中获取
                List<BaseMemberEntity> baseMemberEntityList = baseMemberService.selectByPatNo(patNo);
                if (null != baseMemberEntityList && baseMemberEntityList.size() > 0) {
                    BeanUtils.copyProperties(baseMemberEntityList.get(0), treatment);
                } else {
                    String genderDesc = bookApp.getSex();
                    String dob = bookApp.getDob();
                    if (!StringUtils.isBlank(genderDesc)) {
                        Map<String, Object> param = Maps.newHashMap();
                        param.put("type", SYS_DICT_KEY_SEX);
                        param.put("value", genderDesc);
                        Long sexCode = Long.parseLong(sysDictService.selectByMap(param).get(0).getCode());
                        treatment.setGender(sexCode);
                        treatment.setGenderDesc(genderDesc);
                    }
                    if (!StringUtils.isBlank(dob)) {
                        Date birthDay = DateUtils.formatDate(dob);
                        treatment.setBirthday(birthDay);
                        treatment.setBirth(DateUtil.date2LocalDate(birthDay));
                    }
                    treatment.setPatNo(bookApp.getRegNo() == null ? "" : bookApp.getRegNo());
                    treatment.setName(bookApp.getName() == null ? "" : bookApp.getName());
                    treatment.setAge(bookApp.getAge() == null ? "" : bookApp.getAge());
                    treatment.setPhone(bookApp.getTelNo() == null ? "" : bookApp.getTelNo());
                }

                //根据his_id关联查询  base_order、book_item、base_detection_dep表获取执行科室、检查项目的信息
                String hisId = bookApp.getOrdId() == null ? "" : bookApp.getOrdId();
                BaseOrderEntity baseOrderEntity = baseOrderService.getByHisId(hisId);
                DetectionDep detectionDep = new DetectionDep();
                String detectionDepName = "";
                String bookItemName = "";
                if (null != baseOrderEntity && null != baseOrderEntity.getBookItemEntity()) {
                    bookItemName = baseOrderEntity.getBookItemEntity().getName();
                    BaseDetectionDepEntity baseDetectionDepEntity =baseOrderEntity.getBookItemEntity().getBaseDetectionDepEntity();
                    BeanUtils.copyProperties(baseDetectionDepEntity , detectionDep);
                    detectionDepName = detectionDep.getName();
                    LOGGER.info("hisId:{} 对应的医技科室信息为:{}", hisId, new Gson().toJson(detectionDep));
                }
                //BookApplicationEntity bookApplicationEntity = new BookApplicationEntity();
                //app_data对应的医技科室信息
                bookApp.setDetectionDep(detectionDep);
                bookApp.setDetectionDepName(detectionDepName);
                bookApp.setBookItemName(bookItemName);
                bookApps.add(bookApp);
            }
            bookPatientDetail.setTreatment(treatment);
            bookPatientDetail.setBookApps(bookApps);
        }
        LOGGER.info("获取待预约患者详情：{}", new Gson().toJson(bookPatientDetail));
        return bookPatientDetail;
    }
}
