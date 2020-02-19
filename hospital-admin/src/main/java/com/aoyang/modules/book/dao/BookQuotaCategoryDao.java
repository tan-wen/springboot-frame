package com.aoyang.modules.book.dao;

import com.aoyang.modules.book.entity.BookQuotaCategoryEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * 科室分组（执行科室自定义，住院预约）
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 09:33:16
 */
public interface BookQuotaCategoryDao extends BaseMapper<BookQuotaCategoryEntity> {

    List<BookQuotaCategoryEntity> listPage(Page<BookQuotaCategoryEntity> page, String keyword);
}
