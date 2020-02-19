package com.aoyang.modules.base.dao;

import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 病区
 * 
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-07 08:46:47
 */
public interface BaseInpatientAreaDao extends BaseMapper<BaseInpatientAreaEntity> {
    /**
     *根据his_id获取主键
     */
    BaseInpatientAreaEntity getHisId(String hisId);


}
