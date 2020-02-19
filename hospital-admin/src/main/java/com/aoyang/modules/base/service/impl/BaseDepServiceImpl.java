package com.aoyang.modules.base.service.impl;

import com.aoyang.modules.base.dao.BaseDepDao;
import com.aoyang.modules.base.entity.BaseDepEntity;
import com.aoyang.modules.base.service.BaseDepService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("baseDepService")
public class BaseDepServiceImpl extends ServiceImpl<BaseDepDao, BaseDepEntity> implements BaseDepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BaseDepEntity> page = this.selectPage(
                new Query<BaseDepEntity>(params).getPage(),
                new EntityWrapper<BaseDepEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtils(page);
    }

}
