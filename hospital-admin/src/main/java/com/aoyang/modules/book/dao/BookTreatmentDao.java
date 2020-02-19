package com.aoyang.modules.book.dao;


import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.entity.BookTreatmentEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * 就诊表
 * 
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookTreatmentDao extends BaseMapper<BookTreatmentEntity> {
    BookRecordDetail getBookRecordByAdmNo(Map<String, Object> param);
}
