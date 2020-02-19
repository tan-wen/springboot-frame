package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 号源配置详情表
 *
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2019-12-4 14:11:37
 */
public interface BookQuotaOccupiedService extends IService<BookQuotaDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BookQuotaDetailEntity> selectByDectionAndWeek(Long detectionId, Long weekId);


    /**
     *号源锁定
     * TreatmentCategory:门诊/住院
     *
     * @param startDate
     * @param adminNo
     * @param bookItemId
     * @param bookTime
     * @param weekId
     * @param timeSliceId
     * @return
     */
    void save(String startDate,String adminNo,Long bookItemId,String bookTime, Long weekId,Long detectionId,Long timeSliceId,String  payCategory,String category,String wardId,String depId);



    /**
     *保存 预约记录book_record ，绑定预约记录和 appDat关联关系
     *
     * @param startDate
     * @param adminNo
     * @param bookItemId
     * @param bookTime
     * @param weekId
     * @param timeSliceId
     * @return
     */
    void saveBookRecord(String startDate, String adminNo, Long bookItemId, String bookTime, Long weekId, Long detectionId, Long timeSliceId);


    /**
     * 根据前台传入的id查询号源配置详情临时表(优先)或号源配置详情表获取预约项最大可预约额
     * @param quotaDetailId
     * @return
     */
    Integer getQuotaMax(Long quotaDetailId);



}

