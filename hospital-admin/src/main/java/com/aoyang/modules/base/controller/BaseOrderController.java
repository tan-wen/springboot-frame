package com.aoyang.modules.base.controller;

import com.aoyang.modules.base.entity.BaseOrderEntity;
import com.aoyang.modules.base.service.BaseOrderService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * his侧医嘱项信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-02 10:16:18
 */
@RestController
@RequestMapping("base/baseorder")
public class BaseOrderController {
    @Autowired
    private BaseOrderService baseOrderService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:baseorder:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = baseOrderService.listPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:baseorder:info")
    public R info(@PathVariable("id") Long id) {
        BaseOrderEntity baseOrder = baseOrderService.selectById(id);

        return R.ok().put("baseOrder", baseOrder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:baseorder:save")
    public R save(@RequestBody BaseOrderEntity baseOrder) {
        Long userId = ShiroUtils.getUserId();
        baseOrder.setCreateBy(userId);
        baseOrder.setLastModifyBy(userId);
        baseOrderService.insert(baseOrder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:baseorder:update")
    public R update(@RequestBody BaseOrderEntity baseOrder) {
        ValidatorUtils.validateEntity(baseOrder);
        baseOrder.setLastModifyBy(ShiroUtils.getUserId());
        baseOrderService.updateAllColumnById(baseOrder);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:baseorder:delete")
    public R delete(@RequestBody Long[] ids) {
        baseOrderService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
