package com.aoyang.modules.book.service;

import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.dto.DetectionQuotaOccupied;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-14 13:34:48
 */
public interface BookItemService extends IService<BookItemEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listPage(Map<String, Object> params);

    /**
     *
     * @param detectionId
     * @param weekId
     * @param category : 就诊类型  ： 有 OUTPATIENT(门诊)  、 INPATIENT(住院)、OUTSIDE(外部)、SUM(总名额)
     * @return
     */
    DetectionQuota getQuota(Long detectionId, Long weekId,String[] ordIdList ,String category);

    DetectionQuota getQuotaTemp(Long detectionId, Long weekId,String[] ordIdList ,String category,String checkedDate,String depId);

    /**
     * 获取已占用的配额
     * @param weekId
     * @param checkedDate
     * @param bookItemId
     * @param category
     * @return
     */
    List<DetectionQuotaOccupied> getOccupiedQuota(Long weekId, String checkedDate, Long bookItemId, String category);

    /**
     *
     * @param weekId 预约星期id
     * @param bookTime  预约日期
     * @param bookItemId   预约项目id
     * @param category  预约类别
     * @param timeSliceId  预约时间片段id
     * @return
     */
    Integer getOccupiedQuotaNum(Long weekId, String bookTime, Long bookItemId, String category,Long timeSliceId);


    /**
     *
     *根据执行科室下的检查项目
     * @param params
     * @return
     */
    List<BookItemEntity> getBookItemListByDetectionDeptId(Map<String, Object> params);


}

