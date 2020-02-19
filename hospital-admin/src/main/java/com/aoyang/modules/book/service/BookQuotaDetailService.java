package com.aoyang.modules.book.service;

import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 号源配置详情表
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-19 14:11:37
 */
public interface BookQuotaDetailService extends IService<BookQuotaDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BookQuotaDetailEntity> selectByDectionAndWeek(Long detectionId, Long weekId);
}

