package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.dao.BookQuotaCategoryDepDao;
import com.aoyang.modules.book.entity.BookQuotaCategoryDepEntity;
import com.aoyang.modules.book.service.BookQuotaCategoryDepService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("bookQuotaCategoryDepService")
public class BookQuotaCategoryDepServiceImpl extends ServiceImpl<BookQuotaCategoryDepDao, BookQuotaCategoryDepEntity> implements BookQuotaCategoryDepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookQuotaCategoryDepEntity> page = this.selectPage(
                new Query<BookQuotaCategoryDepEntity>(params).getPage(),
                new EntityWrapper<BookQuotaCategoryDepEntity>()
        );

        return new PageUtils(page);
    }

}
