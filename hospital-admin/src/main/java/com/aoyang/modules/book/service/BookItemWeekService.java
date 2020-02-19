package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookItemWeekEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-23 15:24:16
 */
public interface BookItemWeekService extends IService<BookItemWeekEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

