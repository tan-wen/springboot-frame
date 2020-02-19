package com.aoyang.modules.book.service;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.book.entity.BookQuotaCategoryEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * 配额类别
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 09:33:16
 */
public interface BookQuotaCategoryService extends IService<BookQuotaCategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listPage(Map<String, Object> params);

    void generateAutoDepGroup(BaseDetectionDepEntity baseDetectionDep);
}

