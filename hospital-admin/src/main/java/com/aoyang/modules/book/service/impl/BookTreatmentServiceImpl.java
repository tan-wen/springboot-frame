package com.aoyang.modules.book.service.impl;


import com.aoyang.modules.book.dao.BookTreatmentDao;
import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.entity.BookTreatmentEntity;
import com.aoyang.modules.book.service.BookTreatmentService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("bookTreatmentService")
        public class BookTreatmentServiceImpl extends ServiceImpl<BookTreatmentDao, BookTreatmentEntity> implements BookTreatmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookTreatmentEntity> page = this.selectPage(
                new Query<BookTreatmentEntity>(params).getPage(),
                new EntityWrapper<BookTreatmentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public BookRecordDetail getBookRecordByAdmNo(String admNo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("admNo", admNo);
        return baseMapper.getBookRecordByAdmNo(param);
    }

}
