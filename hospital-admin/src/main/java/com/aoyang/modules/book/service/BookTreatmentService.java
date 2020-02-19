package com.aoyang.modules.book.service;

import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.entity.BookTreatmentEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * 就诊表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookTreatmentService extends IService<BookTreatmentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    BookRecordDetail getBookRecordByAdmNo(String admNo);
}

