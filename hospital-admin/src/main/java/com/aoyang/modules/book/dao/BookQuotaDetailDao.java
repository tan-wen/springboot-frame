package com.aoyang.modules.book.dao;

import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 号源配置详情表
 * 
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-19 14:11:37
 */
public interface BookQuotaDetailDao extends BaseMapper<BookQuotaDetailEntity> {

    List<BookQuotaDetailEntity> selectByDectionAndWeek(Map<String, Object> param);
}
