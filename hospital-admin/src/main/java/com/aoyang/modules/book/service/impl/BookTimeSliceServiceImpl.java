package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.book.dao.BookTimeSliceDao;
import com.aoyang.modules.book.entity.BookTimeSliceEntity;
import com.aoyang.modules.book.service.BookTimeSliceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("bookTimeSliceService")
public class BookTimeSliceServiceImpl extends ServiceImpl<BookTimeSliceDao, BookTimeSliceEntity> implements BookTimeSliceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BookTimeSliceEntity> page = this.selectPage(
                new Query<BookTimeSliceEntity>(params).getPage(),
                new EntityWrapper<BookTimeSliceEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtils(page);
    }

}
