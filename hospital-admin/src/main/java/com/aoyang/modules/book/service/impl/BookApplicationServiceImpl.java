package com.aoyang.modules.book.service.impl;


import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.dao.BookApplicationDao;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.service.BookApplicationService;
import com.aoyang.modules.book.service.ExeStatus;
import com.aoyang.modules.book.service.PayStatus;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookApplicationService")
public class BookApplicationServiceImpl extends ServiceImpl<BookApplicationDao, BookApplicationEntity> implements BookApplicationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookApplicationEntity> page = this.selectPage(
                new Query<BookApplicationEntity>(params).getPage(),
                new EntityWrapper<BookApplicationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public BaseMemberEntity getMemberById(Long appId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("appId", appId);
        return baseMapper.getMemberById(param);
    }


    @Override
    public List<BookApplicationEntity> getAppNotBindBookRecord(Long bookItemId, String admNo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("bookItemId", bookItemId);
        param.put("admNo", admNo);
        return baseMapper.getAppNotBindBookRecord(param);
    }



    @Override
    public List<BookApplicationEntity> getByRecordId(Long bookRecordId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("book_record_id",bookRecordId);
        List<BookApplicationEntity>  bookApplicationEntities = selectByMap(param);
        return bookApplicationEntities;
    }



    @Override
    public BookApplicationEntity getByAppNo(String appNo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("app_no",appNo);
        List<BookApplicationEntity>  bookApplicationEntities = selectByMap(param);
        if(null!=bookApplicationEntities && !bookApplicationEntities.isEmpty()){
            return bookApplicationEntities.get(0);
        }
        return null;
    }

    @Override
    public List<BookApplicationEntity> getOutpatientAndUnpaid() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("treatmentCategory", TreatmentCategory.OUTPATIENT.name());
        List<String> payStatusList = Lists.newArrayList();
        payStatusList.add(PayStatus.TB.name());
        payStatusList.add(PayStatus.B.name());
        param.put("payStatus", payStatusList);
        return baseMapper.findBooked(param);
    }

    @Override
    public List<BookApplicationEntity> getOutpatientAndVerified() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("treatmentCategory", TreatmentCategory.OUTPATIENT.name());
        List<String> exeStatusList = Lists.newArrayList();
        exeStatusList.add(ExeStatus.V.name());
        param.put("exeStatus", exeStatusList);
        return baseMapper.findBooked(param);
    }

    @Override
    public List<BookApplicationEntity> getOutpatientVerifiedAndPaid() {
        Map<String, Object> param = Maps.newHashMap();
        param.put("treatmentCategory", TreatmentCategory.OUTPATIENT.name());
        List<String> exeStatusList = Lists.newArrayList();
        exeStatusList.add(ExeStatus.V.name());
        param.put("exeStatus", exeStatusList);
        List<String> payStatusList = Lists.newArrayList();
        payStatusList.add(PayStatus.P.name());
        param.put("payStatus", payStatusList);
        return baseMapper.findBooked(param);
    }
}
