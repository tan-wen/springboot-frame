package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookTimeSliceEntity;
import com.aoyang.modules.book.service.BookTimeSliceService;
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
import java.util.List;
import java.util.Map;


/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-16 10:33:45
 */
@RestController
@RequestMapping("book/booktimeslice")
public class BookTimeSliceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookTimeSliceController.class);

    @Autowired
    private BookTimeSliceService bookTimeSliceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:booktimeslice:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookTimeSliceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:booktimeslice:info")
    public R info(@PathVariable("id") Long id) {
        BookTimeSliceEntity bookTimeSlice = bookTimeSliceService.selectById(id);

        return R.ok().put("bookTimeSlice", bookTimeSlice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:booktimeslice:save")
    public R save(@RequestBody BookTimeSliceEntity bookTimeSlice) {
        Long userId = ShiroUtils.getUserId();
        bookTimeSlice.setCreateBy(userId);
        bookTimeSlice.setLastModifyBy(userId);
        bookTimeSliceService.insert(bookTimeSlice);
        LOGGER.info("保存的实体信息：{}", new Gson().toJson(bookTimeSlice));
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:booktimeslice:update")
    public R update(@RequestBody BookTimeSliceEntity bookTimeSlice) {
        ValidatorUtils.validateEntity(bookTimeSlice);
        bookTimeSlice.setLastModifyBy(ShiroUtils.getUserId());
        bookTimeSliceService.updateById(bookTimeSlice);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("book:booktimeslice:delete")
    public R delete(@RequestBody Long[] ids) {
        bookTimeSliceService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/all")
    @RequiresPermissions("book:booktimeslice:info")
    public R all() {
        List<BookTimeSliceEntity> entities = bookTimeSliceService.selectList(new Wrapper<BookTimeSliceEntity>() {
            @Override
            public String getSqlSegment() {
                return null;
            }
        });
        return R.ok().put("slices", entities);
    }
}
