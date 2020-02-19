package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookQuotaCategoryDepEntity;
import com.aoyang.modules.book.entity.BookQuotaCategoryEntity;
import com.aoyang.modules.book.service.BookQuotaCategoryDepService;
import com.aoyang.modules.book.service.BookQuotaCategoryService;
import com.aoyang.modules.book.service.TreatmentCategory;
import com.google.common.collect.Maps;
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
import java.util.List;
import java.util.Map;


/**
 * 配额类别
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-30 09:33:16
 */
@RestController
@RequestMapping("book/bookquotacategory")
public class BookQuotaCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookQuotaCategoryController.class);

    @Autowired
    private BookQuotaCategoryService bookQuotaCategoryService;

    @Autowired
    private BookQuotaCategoryDepService bookQuotaCategoryDepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:bookquotacategory:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookQuotaCategoryService.listPage(params);
        LOGGER.info("配额类别列表：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:bookquotacategory:info")
    public R info(@PathVariable("id") Long id) {
        BookQuotaCategoryEntity bookQuotaCategory = bookQuotaCategoryService.selectById(id);
        Map<String, Object> param = Maps.newHashMap();
        param.put("quota_category_id", id);
        List<BookQuotaCategoryDepEntity> deps = bookQuotaCategoryDepService.selectByMap(param);
        return R.ok().put("bookQuotaCategory", bookQuotaCategory)
                .put("deps", deps)
                .put("inpatient", TreatmentCategory.INPATIENT);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:bookquotacategory:save")
    public R save(@RequestBody BookQuotaCategoryEntity bookQuotaCategory) {
        LOGGER.info("保存科室组信息：{}", new Gson().toJson(bookQuotaCategory));
        Long userId = ShiroUtils.getUserId();
        bookQuotaCategory.setCreateBy(userId);
        bookQuotaCategory.setLastModifyBy(userId);
        bookQuotaCategoryService.insert(bookQuotaCategory);
        Long id = bookQuotaCategory.getId();
        for (BookQuotaCategoryDepEntity dep : bookQuotaCategory.getDeps()) {
            dep.setQuotaCategoryId(id);
            dep.setLastModifyBy(userId);
            dep.setCreateBy(userId);
            bookQuotaCategoryDepService.insert(dep);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:bookquotacategory:update")
    public R update(@RequestBody BookQuotaCategoryEntity bookQuotaCategory) {
        ValidatorUtils.validateEntity(bookQuotaCategory);
        LOGGER.info("更新科室组信息：{}", new Gson().toJson(bookQuotaCategory));
        Long userId = ShiroUtils.getUserId();
        Long id = bookQuotaCategory.getId();
        bookQuotaCategory.setLastModifyBy(userId);
        bookQuotaCategoryService.updateAllColumnById(bookQuotaCategory);//全部更新
        for (BookQuotaCategoryDepEntity dep : bookQuotaCategory.getDeps()) {
            if (null == dep.getId()) {
                dep.setCreateBy(userId);
                dep.setLastModifyBy(userId);
                dep.setQuotaCategoryId(id);
                bookQuotaCategoryDepService.insert(dep);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("book:bookquotacategory:delete")
    public R delete(@RequestBody Long[] ids) {
        bookQuotaCategoryService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 删除配额类别科室表记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/deletedepbygroupId")
    public R deleteDepByGroupId(@RequestBody Long id) {
        bookQuotaCategoryDepService.deleteById(id);
        return R.ok();
    }
}
