package com.aoyang.modules.base.service;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMenuEntity;

import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-12 16:52:44
 */
public interface BaseDetectionDepService extends IService<BaseDetectionDepEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存医技科室菜单的同时在根目录生成对应菜单及号源配置菜单
     */
    void generateMenu(BaseDetectionDepEntity baseDetectionDep, SysMenuEntity maxOrderNumEntity);


}

