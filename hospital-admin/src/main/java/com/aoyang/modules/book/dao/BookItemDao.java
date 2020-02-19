package com.aoyang.modules.book.dao;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.dto.DetectionQuotaOccupied;
import com.aoyang.modules.book.entity.BookItemEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-14 13:34:48
 */
public interface BookItemDao extends BaseMapper<BookItemEntity> {

    List<BaseMemberEntity> listPage(Page<BaseMemberEntity> page, String keyword);

    DetectionQuota getQuota(Map<String, Object> param);

    DetectionQuota getLocalQuota(Map<String, Object> param);

    List<DetectionQuotaOccupied> getOccupiedQuota(Map<String, Object> param);


    List<DetectionQuotaOccupied> getOccupiedQuotaNum(Map<String, Object> param);

    List<Long> getByDetectionDepId(Map<String, Object> param);

    List<BookItemEntity> getBookItemListByDetectionDeptId(Map<String, Object> params);
}
