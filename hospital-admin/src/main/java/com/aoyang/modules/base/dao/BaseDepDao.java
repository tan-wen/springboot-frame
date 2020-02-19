package com.aoyang.modules.base.dao;

import com.aoyang.modules.base.entity.BaseDepEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * his侧科室信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 11:09:48
 */
public interface BaseDepDao extends BaseMapper<BaseDepEntity> {

    /**
     *根据his_id获取主键
     */
    BaseDepEntity getHisId(String hisId);

}
