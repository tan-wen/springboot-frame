package com.aoyang.modules.base.service.impl;

import com.aoyang.modules.base.dao.BaseDetectionDepDao;
import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.base.service.BaseDetectionDepService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("baseDetectionDepService")
public class BaseDetectionDepServiceImpl extends ServiceImpl<BaseDetectionDepDao, BaseDetectionDepEntity> implements BaseDetectionDepService {


    @Autowired
    private SysMenuDao sysMenuDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDetectionDepServiceImpl.class);


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        Page<BaseDetectionDepEntity> page = this.selectPage(
                new Query<BaseDetectionDepEntity>(params).getPage(),
                new EntityWrapper<BaseDetectionDepEntity>()
                        .like(StringUtils.isNotBlank(name), "name", name)
        );

        return new PageUtils(page);
    }

    /**
     * 新增和修改时生成菜单
     *
     * @param baseDetectionDep
     */
    @Override
    public void generateMenu(BaseDetectionDepEntity baseDetectionDep, SysMenuEntity maxOrderNumEntity) {
        LOGGER.info("【医技科室】生成修改对应菜单");
        if (baseDetectionDep.getMenuId() == null) {
            //生成主菜单
            SysMenuEntity menu = new SysMenuEntity();
            menu.setOrderNum(maxOrderNumEntity.getOrderNum() + 10);
            menu.setName(baseDetectionDep.getName());
            menu.setParentId(0L);
            menu.setType(0);
            LOGGER.info("【医技科室】主菜单参数:{}", new Gson().toJson(menu));
            sysMenuDao.insert(menu);
            //base_detection_dep绑定menu_id
            baseDetectionDep.setMenuId(menu.getMenuId());
            updateById(baseDetectionDep);
            //生成对应号源配置页
            SysMenuEntity orderMenu = new SysMenuEntity();
            orderMenu.setParentId(menu.getMenuId());
            orderMenu.setName("号源配置-" + baseDetectionDep.getName());
            orderMenu.setType(1);
            orderMenu.setOrderNum(0);
            orderMenu.setUrl("modules/dep/depquota.html?detectionDepId=" + baseDetectionDep.getId());
            LOGGER.info("【医技科室】号源配置菜单参数:{}", new Gson().toJson(orderMenu));
            sysMenuDao.insert(orderMenu);
            //生成对应号源配置临时页
            SysMenuEntity orderTempMenu = new SysMenuEntity();
            orderTempMenu.setParentId(menu.getMenuId());
            orderTempMenu.setName("号源临时配置-" + baseDetectionDep.getName());
            orderTempMenu.setType(1);
            orderTempMenu.setOrderNum(1);
            orderTempMenu.setUrl("modules/dep/depquotatemp.html?detectionDepId=" + baseDetectionDep.getId());
            LOGGER.info("【医技科室】号源临时配置菜单参数:{}", new Gson().toJson(orderTempMenu));
            sysMenuDao.insert(orderTempMenu);
            //生成医技科室预约页
            SysMenuEntity depMenu=new SysMenuEntity();
            depMenu.setParentId(menu.getMenuId());
            depMenu.setName("预约-" + baseDetectionDep.getName());
            depMenu.setType(1);
            depMenu.setOrderNum(2);
            depMenu.setUrl("modules/medicaldetection/book_medicaldetection.html?detectionDepId=" + baseDetectionDep.getId());
            LOGGER.info("【医技科室】医技科室预约菜单参数:{}", new Gson().toJson(depMenu));
            sysMenuDao.insert(depMenu);
        } else {
            SysMenuEntity menu = new SysMenuEntity();
            menu.setName(baseDetectionDep.getName());
            menu.setMenuId(baseDetectionDep.getMenuId());
            LOGGER.info("【医技科室】更新,主菜单参数:{}", new Gson().toJson(menu));
            sysMenuDao.updateById(menu);
            //更新号源配置页
            SysMenuEntity orderMenu = new SysMenuEntity();
            orderMenu.setName("号源配置-" + baseDetectionDep.getName());
            orderMenu.setParentId(baseDetectionDep.getMenuId());
            orderMenu.setOrderNum(0);
            LOGGER.info("【医技科室】更新,号源配置菜单参数:{}", new Gson().toJson(orderMenu));
            sysMenuDao.updateByParentId(orderMenu);
            //更新临时号源配置页
            SysMenuEntity orderTempMenu = new SysMenuEntity();
            orderTempMenu.setName("号源临时配置-" + baseDetectionDep.getName());
            orderTempMenu.setParentId(baseDetectionDep.getMenuId());
            orderTempMenu.setOrderNum(1);
            LOGGER.info("【医技科室】更新,号源临时配置菜单参数:{}", new Gson().toJson(orderTempMenu));
            sysMenuDao.updateByParentId(orderTempMenu);
            //更新医技科室预约页
            SysMenuEntity depMenu = new SysMenuEntity();
            depMenu.setName("预约-" + baseDetectionDep.getName());
            depMenu.setParentId(baseDetectionDep.getMenuId());
            depMenu.setOrderNum(2);
            LOGGER.info("【医技科室】更新,医技科室预约菜单参数:{}", new Gson().toJson(depMenu));
            sysMenuDao.updateByParentId(depMenu);
        }
    }

}
