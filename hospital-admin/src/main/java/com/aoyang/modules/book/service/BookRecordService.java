package com.aoyang.modules.book.service;

import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.dto.BookRecordItem;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.entity.BookRecordEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 预约记录表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookRecordService extends IService<BookRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    BookItemEntity getBookItemById(Long recordId);

    BookRecordEntity getRecordTimeById(Long recordId);

    /**
     * 根据预约记录获取预约记录详情
     * @param bookRecordDetail
     * @return
     */
    List<BookRecordItem> getBookRecordItem(BookRecordDetail bookRecordDetail) ;

}

