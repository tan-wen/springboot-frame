package com.aoyang.modules.base.service.impl;

import com.aoyang.modules.base.dao.BaseOrderDao;
import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.aoyang.modules.base.service.BaseOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("baseOrderService")
public class BaseOrderServiceImpl extends ServiceImpl<BaseOrderDao, BaseOrderEntity> implements BaseOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String arcicName = (String) params.get("arcicName");
        Page<BaseOrderEntity> page = this.selectPage(
                new Query<BaseOrderEntity>(params).getPage(),
                new EntityWrapper<BaseOrderEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
                        .orNew().like(StringUtils.isNotBlank(arcicName), "arcic_name", arcicName)
        );
        return new PageUtils(page);
    }


    @Override
    public PageUtils listPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        Page<BaseOrderEntity> page = new Query<BaseOrderEntity>(params).getPage();
        List<BaseOrderEntity> baseOrderEntityList = baseMapper.listPage(page, keyword);
        return new PageUtils(page.setRecords(baseOrderEntityList));
    }

    @Override
    public BaseOrderEntity getByHisId(String hisId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("hisId", hisId);
        return baseMapper.getDetectionItemDep(param);
    }

    @Override
    public List<String> getHisOrdId(Long detectionDeptId) {
        return baseMapper.getHisOrdId(detectionDeptId);
    }


}
