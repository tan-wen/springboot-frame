package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookWeekTimeSliceEntity;
import com.aoyang.modules.book.service.BookWeekTimeSliceService;
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
 * @date 2019-10-16 10:33:45
 */
@RestController
@RequestMapping("book/bookweektimeslice")
public class BookWeekTimeSliceController {
    @Autowired
    private BookWeekTimeSliceService bookWeekTimeSliceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:bookweektimeslice:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookWeekTimeSliceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:bookweektimeslice:info")
    public R info(@PathVariable("id") Long id) {
        BookWeekTimeSliceEntity bookWeekTimeSlice = bookWeekTimeSliceService.selectById(id);

        return R.ok().put("bookWeekTimeSlice", bookWeekTimeSlice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:bookweektimeslice:save")
    public R save(@RequestBody BookWeekTimeSliceEntity bookWeekTimeSlice) {
        Long userId = ShiroUtils.getUserId();
        bookWeekTimeSlice.setCreateBy(userId);
        bookWeekTimeSlice.setLastModifyBy(userId);
        bookWeekTimeSliceService.insert(bookWeekTimeSlice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:bookweektimeslice:update")
    public R update(@RequestBody BookWeekTimeSliceEntity bookWeekTimeSlice) {
        ValidatorUtils.validateEntity(bookWeekTimeSlice);
        bookWeekTimeSlice.setLastModifyBy(ShiroUtils.getUserId());
        bookWeekTimeSliceService.updateAllColumnById(bookWeekTimeSlice);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bookWeekTimeSliceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
}
