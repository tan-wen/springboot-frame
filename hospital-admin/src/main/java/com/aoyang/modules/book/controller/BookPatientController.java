package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.dto.BookApp;
import com.aoyang.modules.book.dto.BookPatientDetail;
import com.aoyang.modules.book.dto.DetectionDep;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.entity.BookRecordEntity;
import com.aoyang.modules.book.service.BookApplicationService;
import com.aoyang.modules.book.service.BookRecordService;
import com.aoyang.modules.book.service.impl.BookPatientDetailServiceImpl;
import com.google.common.collect.Lists;
import io.renren.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("book/patient")
public class BookPatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookPatientController.class);

    private static final String CONNECTOR = "-";

    private static final String SPACE = " ";

    @Autowired
    private BookPatientDetailServiceImpl bookPatientDetailService;


    @Autowired
    private BookApplicationService bookApplicationService;

    @Autowired
    private BookRecordService bookRecordService;


    @RequestMapping("/detail")
    public R detail(@RequestParam("admNo") String admNo) {
        BookPatientDetail bookPatientDetail = bookPatientDetailService.getPatientDetail(admNo);


        //  将数据遍历出来查询appData 对应的预约记录
        //List<BookApplicationEntity> bookApplicationEntities = bookPatientDetail.getBookApplicationEntitys();
        List<BookApp> bookApps = bookPatientDetail.getBookApps();
        List<DetectionDep> detectionDeps = Lists.newArrayList();
        List<DetectionDep> distinctBaseDetectionDepEntities = Lists.newArrayList();
        if (null != bookApps && !bookApps.isEmpty()) {
            for (BookApp bookApp : bookApps) {
                detectionDeps.add(bookApp.getDetectionDep());
                String appNo = bookApp.getAppNo();
                BookApplicationEntity bookApplicationEntityTable = bookApplicationService.getByAppNo(appNo);
                //数据库中不存在该条appData 数据
                String detectionTime = "";
                if (null != bookApplicationEntityTable) {
                    Long bookRecordId = bookApplicationEntityTable.getBookRecordId();
                    BookRecordEntity bookRecord = bookRecordService.getRecordTimeById(bookRecordId);
                    if (null != bookRecord) {
                        String bookDate = bookRecord.getBookTime();
                        String startTime = bookRecord.getBookTimeSliceEntity().getStartTime().toString().substring(0, 5);
                        String endTime = bookRecord.getBookTimeSliceEntity().getEndTime().toString().substring(0, 5);
                        detectionTime = bookDate + SPACE + startTime + CONNECTOR + endTime;
                    }
                }
                bookApp.setDetectionTime(detectionTime);
            }
        }

        //去重
        detectionDeps.stream().forEach(
                p -> {
                    if (!distinctBaseDetectionDepEntities.contains(p) && null != p.getId()) {
                        distinctBaseDetectionDepEntities.add(p);
                    }
                }
        );


        return R.ok().put("patientDetail", bookPatientDetail).put("detectionDeps", distinctBaseDetectionDepEntities);
    }

}
