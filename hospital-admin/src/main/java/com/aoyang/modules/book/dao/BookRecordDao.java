package com.aoyang.modules.book.dao;


import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.entity.BookRecordEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * 预约记录表
 * 
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookRecordDao extends BaseMapper<BookRecordEntity> {
    BookItemEntity getBookItemById(Map<String, Object> param);

    BookRecordEntity getRecordTimeById(Map<String, Object> param);

}
