package com.aoyang.modules.base.dao;

import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * his侧医嘱项信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-02 10:16:18
 */
public interface BaseOrderDao extends BaseMapper<BaseOrderEntity> {

    List<BaseOrderEntity> listPage(Page<BaseOrderEntity> page,String keyword);


    BaseOrderEntity getDetectionItemDep(Map<String, Object> param);

    Long getBookItemIdByHisId(String hisOrderId);

    List<String> getHisOrdId(Long detectionDeptId);
}
