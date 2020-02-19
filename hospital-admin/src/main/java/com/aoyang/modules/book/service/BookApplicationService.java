package com.aoyang.modules.book.service;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 预约申请单表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookApplicationService extends IService<BookApplicationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    BaseMemberEntity getMemberById(Long appId);

    /**
     * 获取未绑定预约记录的appData
     * @param bookItemId ：预约项目id
     * @param admNo ： 就诊号
     * @return
     */
    List<BookApplicationEntity> getAppNotBindBookRecord(Long bookItemId, String admNo);

    /**
     * 根据预约记录id查找book_application数据
     * @param bookRecordId
     * @return
     */
    List<BookApplicationEntity> getByRecordId(Long bookRecordId) ;


    /**
     *
     * @param appNo : 申请单号
     * @return
     */
    BookApplicationEntity getByAppNo(String appNo) ;

    /**
     * 获取门诊未缴费的，且正确关联了预约记录的申请单列表
     * @return
     */
    List<BookApplicationEntity> getOutpatientAndUnpaid();

    /**
     * 获取门诊、执行状态时已核实（V）的申请单列表
     * @return
     */
    List<BookApplicationEntity> getOutpatientAndVerified();

    /**
     * 获取门诊，执行状态为已核实（V）、支付状态为已结算（P）
     * @return
     */
    List<BookApplicationEntity> getOutpatientVerifiedAndPaid();
}


