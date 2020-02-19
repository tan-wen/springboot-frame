package com.aoyang.modules.book.dao;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.entity.BookWeekEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
public interface BookWeekDao extends BaseMapper<BookWeekEntity> {

    List<BaseMemberEntity> listPage(Page<BaseMemberEntity> page, String keyword);

    int save(BookWeekEntity bookWeek);

    List<BookWeekEntity> queryAll();
}
