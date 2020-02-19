package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookQuotaCategoryDepEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * 科室组科室中间表
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 09:33:16
 */
public interface BookQuotaCategoryDepService extends IService<BookQuotaCategoryDepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

