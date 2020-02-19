package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.dao.BookItemWeekDao;
import com.aoyang.modules.book.entity.BookItemWeekEntity;
import com.aoyang.modules.book.service.BookItemWeekService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("bookItemWeekService")
public class BookItemWeekServiceImpl extends ServiceImpl<BookItemWeekDao, BookItemWeekEntity> implements BookItemWeekService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookItemWeekEntity> page = this.selectPage(
                new Query<BookItemWeekEntity>(params).getPage(),
                new EntityWrapper<BookItemWeekEntity>()
        );

        return new PageUtils(page);
    }

}
