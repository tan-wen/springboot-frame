package com.aoyang.modules.base.task;

import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;
import com.aoyang.modules.base.service.BaseInpatientAreaService;
import com.aoyang.modules.book.webservice.wardment.Wardment;
import com.aoyang.modules.book.webservice.wardment.WardmentWs;
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

@Component("baseInpatientAreaSynchronizer")
public class BaseInpatientAreaSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInpatientAreaSynchronizer.class);

    @Autowired
    private BaseInpatientAreaService baseInpatientAreaService;

    //定时任务方法b
    public void updateBaseInpatientArea() {
        //获取所有病区信息
        List<Wardment> wardmentList = WardmentWs.getWardmentList();
        //根据his_id更新表
        LOGGER.info("定时任务:开始更新 base_inpatient_area");
        for (Wardment wardment : wardmentList) {
            BaseInpatientAreaEntity baseInpatientAreaEntity = new BaseInpatientAreaEntity();
            String wardId =wardment.getWardId();
            baseInpatientAreaEntity.setHisId(wardId);
            baseInpatientAreaEntity.setName(wardment.getWardCode());
            baseInpatientAreaEntity.setDescription(wardment.getWardDesc());
            baseInpatientAreaEntity.setLastModifyTime(new Date());
            baseInpatientAreaEntity.setLastModifyBy((long) Constant.SUPER_ADMIN);
            Map<String, Object> param = Maps.newHashMap();
            param.put("his_id", wardId);
            List<BaseInpatientAreaEntity> inpatientAreas=baseInpatientAreaService.selectByMap(param);
            if(null!=inpatientAreas && !inpatientAreas.isEmpty()){
                baseInpatientAreaService.update(baseInpatientAreaEntity, new Wrapper<BaseInpatientAreaEntity>() {
                    @Override
                    public String getSqlSegment() {
                        return "and his_id='" + wardId + "'";
                    }
                });
            }else{
                baseInpatientAreaService.insert(baseInpatientAreaEntity);
            }
        }
        LOGGER.info("定时任务: base_inpatient_area更新完成");
    }

}
