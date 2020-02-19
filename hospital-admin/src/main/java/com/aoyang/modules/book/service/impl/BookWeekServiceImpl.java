package com.aoyang.modules.book.service.impl;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.dao.BookWeekDao;
import com.aoyang.modules.book.entity.BookWeekEntity;
import com.aoyang.modules.book.service.BookWeekService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("bookWeekService")
public class BookWeekServiceImpl extends ServiceImpl<BookWeekDao, BookWeekEntity> implements BookWeekService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BookWeekEntity> page = this.selectPage(
                new Query<BookWeekEntity>(params).getPage(),
                new EntityWrapper<BookWeekEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BaseMemberEntity> page = new Query<BaseMemberEntity>(params).getPage();
        List<BaseMemberEntity> baseMemberEntities = baseMapper.listPage(page, name);
        return new PageUtils(page.setRecords(baseMemberEntities));
    }

    @Override
    public int save(BookWeekEntity bookWeek) {
        return baseMapper.save(bookWeek);
    }

    @Override
    public List<BookWeekEntity> queryAll() {
        return baseMapper.queryAll();
    }

}
