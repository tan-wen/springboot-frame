package com.aoyang.modules.base.service;

import com.aoyang.modules.base.entity.BaseDepEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.Map;

/**
 * his侧科室信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 11:09:48
 */
public interface BaseDepService extends IService<BaseDepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

