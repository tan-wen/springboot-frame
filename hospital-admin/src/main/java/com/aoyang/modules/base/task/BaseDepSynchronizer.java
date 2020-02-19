package com.aoyang.modules.base.task;

import com.aoyang.modules.base.entity.BaseDepEntity;
import com.aoyang.modules.base.service.BaseDepService;
import com.aoyang.modules.book.webservice.deptment.Deptment;
import com.aoyang.modules.book.webservice.deptment.GetDeptmentWs;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Maps;
import io.renren.common.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("baseDepSynchronizer")
public class BaseDepSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDepSynchronizer.class);

    @Autowired
    private BaseDepService baseDepService;

    //定时任务方法
    public void updateBaseDep() {
        //获取所有科室
        List<Deptment> baseDepList = GetDeptmentWs.getAllDepartment();
        //根据his_id更新表
        LOGGER.info("定时任务:开始更新base_dep");
        for (Deptment deptment : baseDepList) {
            BaseDepEntity baseDepEntity = new BaseDepEntity();
            int hisId = Integer.parseInt(deptment.getDeptID());
            baseDepEntity.setHisId(hisId);
            baseDepEntity.setCode(deptment.getDeptCode());
            baseDepEntity.setName(deptment.getDeptName());
            baseDepEntity.setAddress(deptment.getDeptAddress());
            baseDepEntity.setDeptCategory(deptment.getDeptType());
            baseDepEntity.setLastModifyTime(new Date());
            baseDepEntity.setLastModifyBy((long) Constant.SUPER_ADMIN);
            Map<String, Object> param = Maps.newHashMap();
            param.put("his_id", hisId);
            List<BaseDepEntity> record = baseDepService.selectByMap(param);
            if (null != record && !record.isEmpty()) {
                baseDepService.update(baseDepEntity, new Wrapper<BaseDepEntity>() {
                    @Override
                    public String getSqlSegment() {
                        return "and his_id='" + baseDepEntity.getHisId() + "'";
                    }
                });
            } else {
                baseDepService.insert(baseDepEntity);
            }
        }
        LOGGER.info("定时任务:base_dep更新完成");
    }


}
