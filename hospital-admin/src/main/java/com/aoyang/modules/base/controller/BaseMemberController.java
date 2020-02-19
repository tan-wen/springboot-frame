package com.aoyang.modules.base.controller;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.base.service.BaseMemberService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-08 09:58:35
 */
@RestController
@RequestMapping("base/basemember")
public class BaseMemberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMemberController.class);

    @Autowired
    private BaseMemberService baseMemberService;

    public BaseMemberController() {
        super();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:basemember:list")
    public R list(@RequestParam Map<String, Object> params) {
        LOGGER.info("【病患管理】获取列表数据，参数：{}", new Gson().toJson(params));
        PageUtils page = baseMemberService.listPage(params);
        LOGGER.info("【病患管理】获取列表数据：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:basemember:info")
    public R info(@PathVariable("id") Long id) {
        LOGGER.info("【病患管理】获取病患信息，参数：{}", id);
        BaseMemberEntity baseMember = baseMemberService.selectById(id);
        LOGGER.info("【病患管理】获取病患信息：{}", new Gson().toJson(baseMember));
        return R.ok().put("baseMember", baseMember);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    /*@RequiresPermissions("base:basemember:save")*/
    public R save(@RequestBody BaseMemberEntity baseMember) {
        //保存前先判断是否已经有相同病人登记号
        int result = baseMemberService.selectCount(new Wrapper<BaseMemberEntity>() {
            @Override
            public String getSqlSegment() {
                return "and pat_no='" + baseMember.getPatNo() + "'";
            }
        });
        if (result == 0) {
            LOGGER.info("【病患管理】保存，参数：{}", new Gson().toJson(baseMember));
            Long userId = ShiroUtils.getUserId();
            baseMember.setCreateBy(userId);
            baseMember.setLastModifyBy(userId);
            baseMemberService.insert(baseMember);
            return R.ok();
        } else {
            LOGGER.info("【病患管理】保存:病人登记号重复");
            return R.error().put("msg", "该登记号已被使用");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    /* @RequiresPermissions("base:basemember:update")*/
    public R update(@RequestBody BaseMemberEntity baseMember) {
        ValidatorUtils.validateEntity(baseMember);
        int result = baseMemberService.selectCount(new Wrapper<BaseMemberEntity>() {
            @Override
            public String getSqlSegment() {
                return "and pat_no='" + baseMember.getPatNo() + "'" +
                        "and id !='" + baseMember.getId() + "'";
            }
        });
        if (result == 0) {
            LOGGER.info("【病患管理】更新，参数：{}", new Gson().toJson(baseMember));
            baseMember.setLastModifyBy(ShiroUtils.getUserId());
            baseMemberService.updateById(baseMember);
            return R.ok();
        } else {
            LOGGER.info("【病患管理】更新:病人登记号重复");
            return R.error().put("msg", "该登记号已被使用");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:basemember:delete")
    public R delete(@RequestBody Long[] ids) {
        LOGGER.info("【病患管理】删除，参数：{}", new Gson().toJson(ids));
        baseMemberService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
