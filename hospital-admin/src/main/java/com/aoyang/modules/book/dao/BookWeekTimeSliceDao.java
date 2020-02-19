package com.aoyang.modules.book.dao;

import com.aoyang.modules.book.entity.BookWeekTimeSliceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
public interface BookWeekTimeSliceDao extends BaseMapper<BookWeekTimeSliceEntity> {

    List<BookWeekTimeSliceEntity> getByWeekId(Long id);
}
