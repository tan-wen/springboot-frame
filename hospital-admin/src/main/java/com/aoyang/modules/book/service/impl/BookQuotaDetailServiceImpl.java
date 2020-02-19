package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.dao.BookQuotaDetailDao;
import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.aoyang.modules.book.service.BookQuotaDetailService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookQuotaDetailService")
public class BookQuotaDetailServiceImpl extends ServiceImpl<BookQuotaDetailDao, BookQuotaDetailEntity> implements BookQuotaDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookQuotaDetailEntity> page = this.selectPage(
                new Query<BookQuotaDetailEntity>(params).getPage(),
                new EntityWrapper<BookQuotaDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BookQuotaDetailEntity> selectByDectionAndWeek(Long detectionId, Long weekId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("detectionId", detectionId);
        param.put("weekId", weekId);
        return baseMapper.selectByDectionAndWeek(param);
    }

}
