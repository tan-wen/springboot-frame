package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookWeekEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
public interface BookWeekService extends IService<BookWeekEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listPage(Map<String, Object> params);

    int save(BookWeekEntity bookWeek);

    List<BookWeekEntity> queryAll();
}

