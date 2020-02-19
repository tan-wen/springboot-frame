package com.aoyang.modules.base.controller;

import com.aoyang.modules.base.entity.BaseInpatientAreaEntity;
import com.aoyang.modules.base.service.BaseInpatientAreaService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 病区
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-07 08:46:47
 */
@RestController
@RequestMapping("base/baseinpatientarea")
public class BaseInpatientAreaController {
    @Autowired
    private BaseInpatientAreaService baseInpatientAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:baseinpatientarea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseInpatientAreaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:baseinpatientarea:info")
    public R info(@PathVariable("id") Long id){
        BaseInpatientAreaEntity baseInpatientArea = baseInpatientAreaService.selectById(id);

        return R.ok().put("baseInpatientArea", baseInpatientArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:baseinpatientarea:save")
    public R save(@RequestBody BaseInpatientAreaEntity baseInpatientArea){
        baseInpatientAreaService.insert(baseInpatientArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:baseinpatientarea:update")
    public R update(@RequestBody BaseInpatientAreaEntity baseInpatientArea){
        ValidatorUtils.validateEntity(baseInpatientArea);
        baseInpatientAreaService.updateAllColumnById(baseInpatientArea);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:baseinpatientarea:delete")
    public R delete(@RequestBody Long[] ids){
        baseInpatientAreaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/all")
    public R all() {
        List<BaseInpatientAreaEntity> baseInpatientAreaEntities = baseInpatientAreaService.selectByMap(null);
        return R.ok().put("baseInpatientAreas", baseInpatientAreaEntities);
    }

}
