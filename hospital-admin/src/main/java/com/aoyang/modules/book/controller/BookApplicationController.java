package com.aoyang.modules.book.controller;


import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.service.BookApplicationService;
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
 * 预约申请单表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
@RestController
@RequestMapping("base/bookapplication")
public class BookApplicationController {
    @Autowired
    private BookApplicationService bookApplicationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BookApplicationController.class);

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:bookapplication:list")
    public R list(@RequestParam Map<String, Object> params) {
        LOGGER.info("【预约申请单表】获取列表数据，参数：{}", new Gson().toJson(params));
        PageUtils page = bookApplicationService.queryPage(params);
        LOGGER.info("【预约申请单表】获取列表数据：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:bookapplication:info")
    public R info(@PathVariable("id") Long id) {
        LOGGER.info("【预约申请单表】获取信息，参数：{}", id);
        BookApplicationEntity bookApplication = bookApplicationService.selectById(id);
        LOGGER.info("【预约申请单表】获取信息：{}", new Gson().toJson(bookApplication));
        return R.ok().put("bookApplication", bookApplication);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:bookapplication:save")
    public R save(@RequestBody BookApplicationEntity bookApplication) {
        LOGGER.info("【预约申请单表】保存，参数：{}", new Gson().toJson(bookApplication));
        Long userId = ShiroUtils.getUserId();
        bookApplication.setCreateBy(userId);
        bookApplication.setLastModifyBy(userId);
        bookApplicationService.insert(bookApplication);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:bookapplication:update")
    public R update(@RequestBody BookApplicationEntity bookApplication) {
        LOGGER.info("【预约申请单表】更新，参数：{}", new Gson().toJson(bookApplication));
        ValidatorUtils.validateEntity(bookApplication);
        bookApplication.setLastModifyBy(ShiroUtils.getUserId());
        bookApplicationService.updateAllColumnById(bookApplication);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:bookapplication:delete")
    public R delete(@RequestBody Long[] ids) {
        LOGGER.info("【预约申请单表】删除，参数：{}", new Gson().toJson(ids));
        bookApplicationService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }



}
