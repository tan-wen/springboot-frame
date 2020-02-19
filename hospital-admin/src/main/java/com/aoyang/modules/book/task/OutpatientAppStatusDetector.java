package com.aoyang.modules.book.task;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.service.BookApplicationService;
import com.aoyang.modules.book.service.BookRecordService;
import com.aoyang.modules.book.service.PayStatus;
import com.aoyang.modules.book.webservice.outpatient.AppDataStatus;
import com.aoyang.modules.book.webservice.outpatient.AppDataStatusWs;
import com.aoyang.modules.book.util.BookSmsUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 门诊检查申请单状态探测仪
 */
@Component("outpatientAppStatusDetector")
public class OutpatientAppStatusDetector {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutpatientAppStatusDetector.class);

    @Autowired
    private BookApplicationService bookApplicationService;

    @Autowired
    private BookRecordService bookRecordService;

    @Value("${sysconfig.waiting-pay-max}")
    private long waitingPayMax;

    //定时任务方法
    public void exeTask() {
        unpaid();
        paid();
    }

    /*
     * 未缴费
     */
    private void unpaid() {
        List<BookApplicationEntity> unpaidList = bookApplicationService.getOutpatientAndUnpaid();
        LOGGER.info("获取未缴费的检查申请单：{}", new Gson().toJson(unpaidList));
        for (BookApplicationEntity entity : unpaidList) {
            AppDataStatus status = AppDataStatusWs.getStatus(entity.getOeOrditemId());
            Long bookRecordId = entity.getBookRecordId();
            switch (status.getPayStatus()) {
                case P:
                    /*
                     * 支付状态由未支付改变为已支付
                     *    查看当前检查申请单共用的预约记录的其他申请单是否完成支付
                     *      是：发送项目预约成功短信
                     */
                    if (isLastPaidApp(bookRecordId)) {
                        sendBookSuccessMsg(entity.getId(), bookRecordId);
                    }
                    break;
                case B:
                case TB:
                    /*
                     * 支付状态仍然是未支付
                     *  判断是否超时
                     *      是，且相同预约记录下的申请单都未支付（或都已退费、红冲）：取消占位
                     */
                    if (isAppOvertime(entity)) {
                        cancelBookRecord(bookRecordId);
                    }
                    break;
                default:
                    // 其他状态，取消占位
                    cancelBookRecord(bookRecordId);
            }
            // 更新缴费状态
            entity.setPayStatus(status.getPayStatus().name());
            bookApplicationService.updateById(entity);
        }

    }

    /*
     * 已缴费：检查申请单的执行状态初始值是V-已核实
     */
    private void paid() {

        // 1、查询原执行状态为V的申请单，更新其执行状态
        List<BookApplicationEntity> entityList = bookApplicationService.getOutpatientAndVerified();
        LOGGER.info("查询执行状态为V的申请单：{}", new Gson().toJson(entityList));
        for (BookApplicationEntity entity : entityList) {
            AppDataStatus status = AppDataStatusWs.getStatus(entity.getOeOrditemId());
            // 更新执行状态
            entity.setExeStatus(status.getExeStatus().name());
            bookApplicationService.updateById(entity);
        }

        // 2、查找执行状态为V，缴费状态为P的申请单，如果缴费状态变为I-已停止或R-红冲，则进行取消占位
        List<BookApplicationEntity> entities = bookApplicationService.getOutpatientVerifiedAndPaid();
        LOGGER.info("查找执行状态为V，缴费状态为P的申请单：{}", new Gson().toJson(entities));
        for (BookApplicationEntity entity : entities) {
            AppDataStatus status = AppDataStatusWs.getStatus(entity.getOeOrditemId());
            switch (status.getPayStatus()) {
                case I:
                case R:
                    cancelBookRecord(entity.getBookRecordId());
                    break;
            }
            // 更新缴费状态
            entity.setPayStatus(status.getPayStatus().name());
            bookApplicationService.updateById(entity);
        }

    }

    /*
     * 取消占位
     *  共用预约记录的申请单，没有处于已缴费的状态
     */
    private void cancelBookRecord(Long bookRecordId) {
        List<BookApplicationEntity> entities = bookApplicationService.selectList(new Wrapper<BookApplicationEntity>() {
            @Override
            public String getSqlSegment() {
                return " and book_record_id = '" + bookRecordId + "' and pay_status = '" + PayStatus.P.name() + "'";
            }
        });
        if (entities.isEmpty()) {
            LOGGER.info("预约记录：{} 取消占位", bookRecordId);
            bookRecordService.deleteById(bookRecordId);
        }
    }

    /*
     * 检查申请单是否超时
     *  true代表是，false代表否
     */
    private boolean isAppOvertime(BookApplicationEntity entity) {
        Date createTime = entity.getCreateTime();
        if (null == createTime) {
            LOGGER.warn("检查申请单创建时间为空{}", new Gson().toJson(entity));
            return true;
        }
        return new Date().getTime() > createTime.getTime() + waitingPayMax;
    }

    /*
     * 发送预约项目成功的短信
     * @param entity
     */
    private void sendBookSuccessMsg(Long appId, Long recordId) {
        if (null == appId || null == recordId) {
            return;
        }
        BaseMemberEntity member = bookApplicationService.getMemberById(appId);
        BookItemEntity bookItem = bookRecordService.getBookItemById(recordId);
        BookSmsUtils.sendBookSuccessMsg(member.getPhone(), member.getName(), bookItem.getName());
    }

    /*
     * 当前申请单是共用的预约记录中最后一个完成支付的
     * @param bookRecordId : 预约记录id
     * @return true: 是, false : 否
     */
    private boolean isLastPaidApp(Long bookRecordId) {
        if (null == bookRecordId) {
            return false;
        }
        List<BookApplicationEntity> entities = bookApplicationService.selectList(new Wrapper<BookApplicationEntity>() {
            @Override
            public String getSqlSegment() {
                return "and book_record_id = '" + bookRecordId
                        + "' and (pay_status = '" + PayStatus.TB.name() + "' or pay_status = '" + PayStatus.B.name() + "')";
            }
        });
        return entities.size() <= 1;
    }
}
