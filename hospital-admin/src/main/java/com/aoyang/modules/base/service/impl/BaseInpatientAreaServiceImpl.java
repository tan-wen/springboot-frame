package com.aoyang.modules.base.service.impl;

import com.aoyang.modules.base.dao.BaseInpatientAreaDao;
import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;
import com.aoyang.modules.base.service.BaseInpatientAreaService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("baseInpatientAreaService")
public class BaseInpatientAreaServiceImpl extends ServiceImpl<BaseInpatientAreaDao, BaseInpatientAreaEntity> implements BaseInpatientAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        Page<BaseInpatientAreaEntity> page = this.selectPage(
                new Query<BaseInpatientAreaEntity>(params).getPage(),
                new EntityWrapper<BaseInpatientAreaEntity>()
                .like(StringUtils.isNotBlank(keyword),"name",keyword)
        );

        return new PageUtils(page);
    }


}
