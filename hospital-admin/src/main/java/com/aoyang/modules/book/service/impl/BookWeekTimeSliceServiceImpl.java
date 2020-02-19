package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.dao.BookWeekTimeSliceDao;
import com.aoyang.modules.book.entity.BookWeekTimeSliceEntity;
import com.aoyang.modules.book.service.BookWeekTimeSliceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookWeekTimeSliceService")
public class BookWeekTimeSliceServiceImpl extends ServiceImpl<BookWeekTimeSliceDao, BookWeekTimeSliceEntity> implements BookWeekTimeSliceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookWeekTimeSliceEntity> page = this.selectPage(
                new Query<BookWeekTimeSliceEntity>(params).getPage(),
                new EntityWrapper<BookWeekTimeSliceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BookWeekTimeSliceEntity> getByWeekId(Long id) {
        return baseMapper.getByWeekId(id);
    }

}
