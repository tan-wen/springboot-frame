package com.aoyang.modules.base.dao;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-12 16:52:44
 */
public interface BaseDetectionDepDao extends BaseMapper<BaseDetectionDepEntity> {

    List<BaseDetectionDepEntity> queryAll();


}
