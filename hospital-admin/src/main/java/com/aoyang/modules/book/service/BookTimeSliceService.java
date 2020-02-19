package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookTimeSliceEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
public interface BookTimeSliceService extends IService<BookTimeSliceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

