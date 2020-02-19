package com.aoyang.modules.base.controller;

import com.aoyang.modules.base.entity.BaseDetectionDepEntity;
import com.aoyang.modules.base.service.BaseDetectionDepService;
import com.aoyang.modules.book.service.BookQuotaCategoryService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-12 16:52:44
 */
@RestController
@RequestMapping("base/basedetectiondep")
public class BaseDetectionDepController {

    @Autowired
    private BaseDetectionDepService baseDetectionDepService;

    @Autowired
    private BookQuotaCategoryService bookQuotaCategoryService;

    @Autowired
    private SysMenuService sysMenuService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDetectionDepController.class);

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:basedetectiondep:list")
    public R list(@RequestParam Map<String, Object> params) {
        LOGGER.info("【医技科室】获取列表数据，参数：{}", new Gson().toJson(params));
        PageUtils page = baseDetectionDepService.queryPage(params);
        LOGGER.info("【医技科室】获取列表数据：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:basedetectiondep:info")
    public R info(@PathVariable("id") Long id) {
        LOGGER.info("【医技科室】获取医嘱信息，参数：{}", id);
        BaseDetectionDepEntity baseDetectionDep = baseDetectionDepService.selectById(id);
        LOGGER.info("【医技科室】获取医嘱信息：{}", new Gson().toJson(baseDetectionDep));
        return R.ok().put("baseDetectionDep", baseDetectionDep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:basedetectiondep:save")
    public R save(@RequestBody BaseDetectionDepEntity baseDetectionDep) {
        LOGGER.info("【医技科室】保存，参数：{}", new Gson().toJson(baseDetectionDep));
        Long curUserId = ShiroUtils.getUserId();
        baseDetectionDep.setCreateBy(curUserId);
        baseDetectionDep.setLastModifyBy(curUserId);
        baseDetectionDepService.insert(baseDetectionDep);
        SysMenuEntity maxOrderNumEntity = sysMenuService.selectOne(new Wrapper<SysMenuEntity>() {
            @Override
            public String getSqlSegment() {
                return "order by order_num desc limit 1";

            }
        });
        baseDetectionDepService.generateMenu(baseDetectionDep, maxOrderNumEntity);
        bookQuotaCategoryService.generateAutoDepGroup(baseDetectionDep);
        return R.ok().put("maxOrderNumEntity", maxOrderNumEntity);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:basedetectiondep:update")
    public R update(@RequestBody BaseDetectionDepEntity baseDetectionDep) {
        LOGGER.info("【医技科室】更新，参数：{}", new Gson().toJson(baseDetectionDep));
        ValidatorUtils.validateEntity(baseDetectionDep);
        baseDetectionDep.setLastModifyBy(ShiroUtils.getUserId());
        SysMenuEntity maxOrderNumEntity = sysMenuService.selectOne(new Wrapper<SysMenuEntity>() {
            @Override
            public String getSqlSegment() {
                return "order by order_num desc limit 1";
            }
        });
        baseDetectionDepService.generateMenu(baseDetectionDep, maxOrderNumEntity);
        baseDetectionDepService.updateAllColumnById(baseDetectionDep);//全部更新
        bookQuotaCategoryService.generateAutoDepGroup(baseDetectionDep);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:basedetectiondep:delete")
    public R delete(@RequestBody Long[] ids) {
        LOGGER.info("【医技科室】删除，参数：{}", new Gson().toJson(ids));
        List<Long> idList = Arrays.asList(ids);
        //删除时获得id的数组
        for (Long id : idList) {
            BaseDetectionDepEntity baseDetectionDep = baseDetectionDepService.selectById(id);
            Long menuId = baseDetectionDep.getMenuId();
            sysMenuService.delete(menuId);
            sysMenuService.delete(new Wrapper<SysMenuEntity>() {
                @Override
                public String getSqlSegment() {
                    return "where parent_id='" + menuId + "'";
                }
            });
        }
        baseDetectionDepService.deleteBatchIds(idList);
        return R.ok();
    }

    @RequestMapping("/all")
    /*@RequiresPermissions("base:basedetectiondep:list")*/
    public R all() {
        List<BaseDetectionDepEntity> entities = baseDetectionDepService.selectList(new Wrapper<BaseDetectionDepEntity>() {
            @Override
            public String getSqlSegment() {
                return null;
            }
        });
        return R.ok().put("detectionDeps", entities);
    }

}
