package com.aoyang.modules.base.controller;

import com.aoyang.modules.base.entity.BaseDepEntity;
import com.aoyang.modules.base.service.BaseDepService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * his侧科室信息
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 11:09:48
 */
@RestController
@RequestMapping("base/basedep")
public class BaseDepController {
    @Autowired
    private BaseDepService baseDepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:basedep:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = baseDepService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:basedep:info")
    public R info(@PathVariable("id") Long id) {
        BaseDepEntity baseDep = baseDepService.selectById(id);

        return R.ok().put("baseDep", baseDep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:basedep:save")
    public R save(@RequestBody BaseDepEntity baseDep) {
        Long userId = ShiroUtils.getUserId();
        baseDep.setCreateBy(userId);
        baseDep.setLastModifyBy(userId);
        baseDepService.insert(baseDep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:basedep:update")
    public R update(@RequestBody BaseDepEntity baseDep) {
        ValidatorUtils.validateEntity(baseDep);
        baseDep.setLastModifyBy(ShiroUtils.getUserId());
        baseDepService.updateAllColumnById(baseDep);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:basedep:delete")
    public R delete(@RequestBody Long[] ids) {
        baseDepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/all")
    @RequiresPermissions("base:basedep:list")
    public R all() {
        List<BaseDepEntity> deps = baseDepService.selectList(new Wrapper<BaseDepEntity>() {
            @Override
            public String getSqlSegment() {
                return "and name <> ''";
            }
        });
        return R.ok().put("deps", deps);
    }
}
