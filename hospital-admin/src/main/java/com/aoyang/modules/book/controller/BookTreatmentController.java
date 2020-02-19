package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookTreatmentEntity;
import com.aoyang.modules.book.service.BookTreatmentService;
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
 * 就诊表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
@RestController
@RequestMapping("base/booktreatment")
public class BookTreatmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookTreatmentController.class);
    @Autowired
    private BookTreatmentService bookTreatmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:booktreatment:list")
    public R list(@RequestParam Map<String, Object> params){
        LOGGER.info("【门诊就诊记录】获取列表数据，参数：{}", new Gson().toJson(params));
        PageUtils page = bookTreatmentService.queryPage(params);
        LOGGER.info("【门诊就诊记录】获取列表数据：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:booktreatment:info")
    public R info(@PathVariable("id") Long id){
        LOGGER.info("【门诊就诊记录】获取病患信息，参数：{}", id);
        BookTreatmentEntity bookTreatment = bookTreatmentService.selectById(id);
        LOGGER.info("【门诊就诊记录】获取病患信息：{}", new Gson().toJson(bookTreatment));

        return R.ok().put("bookTreatment", bookTreatment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:booktreatment:save")
    public R save(@RequestBody BookTreatmentEntity bookTreatment){
        LOGGER.info("【门诊就诊记录】保存，参数：{}", new Gson().toJson(bookTreatment));
        Long userId = ShiroUtils.getUserId();
        bookTreatment.setCreateBy(userId);
        bookTreatment.setLastModifyBy(userId);
        bookTreatmentService.insert(bookTreatment);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:booktreatment:update")
    public R update(@RequestBody BookTreatmentEntity bookTreatment){
        LOGGER.info("【门诊就诊记录】更新，参数：{}", new Gson().toJson(bookTreatment));
        ValidatorUtils.validateEntity(bookTreatment);
        bookTreatment.setLastModifyBy(ShiroUtils.getUserId());
        bookTreatmentService.updateAllColumnById(bookTreatment);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:booktreatment:delete")
    public R delete(@RequestBody Long[] ids){
        LOGGER.info("【门诊就诊记录】删除，参数：{}", new Gson().toJson(ids));
        bookTreatmentService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


}
