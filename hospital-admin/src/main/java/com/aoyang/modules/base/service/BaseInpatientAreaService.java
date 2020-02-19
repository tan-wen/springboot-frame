package com.aoyang.modules.base.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;

import java.util.Map;

/**
 * 病区
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-07 08:46:47
 */
public interface BaseInpatientAreaService extends IService<BaseInpatientAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

