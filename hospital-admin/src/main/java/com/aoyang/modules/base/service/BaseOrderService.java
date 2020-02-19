package com.aoyang.modules.base.service;

import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * his侧医嘱项信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-02 10:16:18
 */
public interface BaseOrderService extends IService<BaseOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listPage(Map<String, Object> params);

    /**
     * 根据his_id信息查询检查项名称和执行科室
     * @param hisId
     * @return
     */
    BaseOrderEntity getByHisId(String hisId);

    /**
     * 获取指定医技科室关注的基础医嘱id
     *
     * @param detectionDeptId
     * @return
     */
    List<String> getHisOrdId(Long detectionDeptId);
}

