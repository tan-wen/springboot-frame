package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookItemWeekEntity;
import com.aoyang.modules.book.service.BookItemWeekService;
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
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-23 15:24:16
 */
@RestController
@RequestMapping("book/bookitemweek")
public class BookItemWeekController {
    @Autowired
    private BookItemWeekService bookItemWeekService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:bookitemweek:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookItemWeekService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:bookitemweek:info")
    public R info(@PathVariable("id") Long id) {
        BookItemWeekEntity bookItemWeek = bookItemWeekService.selectById(id);

        return R.ok().put("bookItemWeek", bookItemWeek);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:bookitemweek:save")
    public R save(@RequestBody BookItemWeekEntity bookItemWeek) {
        Long userId = ShiroUtils.getUserId();
        bookItemWeek.setCreateBy(userId);
        bookItemWeek.setLastModifyBy(userId);
        bookItemWeekService.insert(bookItemWeek);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:bookitemweek:update")
    public R update(@RequestBody BookItemWeekEntity bookItemWeek) {
        ValidatorUtils.validateEntity(bookItemWeek);
        bookItemWeekService.updateAllColumnById(bookItemWeek);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bookItemWeekService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
