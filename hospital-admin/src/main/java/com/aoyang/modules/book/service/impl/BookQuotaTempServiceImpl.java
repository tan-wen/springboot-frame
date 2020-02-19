package com.aoyang.modules.book.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import com.aoyang.modules.book.dao.BookQuotaTempDao;
import com.aoyang.modules.book.entity.BookQuotaTempEntity;
import com.aoyang.modules.book.service.BookQuotaTempService;


@Service("bookQuotaTempService")
public class BookQuotaTempServiceImpl extends ServiceImpl<BookQuotaTempDao, BookQuotaTempEntity> implements BookQuotaTempService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookQuotaTempEntity> page = this.selectPage(
                new Query<BookQuotaTempEntity>(params).getPage(),
                new EntityWrapper<BookQuotaTempEntity>()
        );

        return new PageUtils(page);
    }

}
