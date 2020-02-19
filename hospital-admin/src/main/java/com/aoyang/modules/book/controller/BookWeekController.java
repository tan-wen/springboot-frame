package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookWeekEntity;
import com.aoyang.modules.book.entity.BookWeekTimeSliceEntity;
import com.aoyang.modules.book.service.BookWeekService;
import com.aoyang.modules.book.service.BookWeekTimeSliceService;
import com.google.common.collect.Maps;
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
@RequestMapping("book/bookweek")
public class BookWeekController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookWeekController.class);

    @Autowired
    private BookWeekService bookWeekService;

    @Autowired
    private BookWeekTimeSliceService bookWeekTimeSliceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:bookweek:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookWeekService.listPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:bookweek:info")
    public R info(@PathVariable("id") Long id) {
        BookWeekEntity bookWeek = bookWeekService.selectById(id);
        List<BookWeekTimeSliceEntity> slices = bookWeekTimeSliceService.getByWeekId(id);
        return R.ok().put("bookWeek", bookWeek).put("slices", slices);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:bookweek:save")
    public R save(@RequestBody BookWeekEntity bookWeek) {
        LOGGER.info("开始保存：{}", bookWeek);
        Long userId = ShiroUtils.getUserId();
        bookWeek.setCreateBy(userId);
        bookWeek.setLastModifyBy(userId);
        bookWeekService.save(bookWeek);
        Long bookWeekId = bookWeek.getId();
        for (BookWeekTimeSliceEntity entity : bookWeek.getSlices()) {
            entity.setCreateBy(userId);
            entity.setLastModifyBy(userId);
            entity.setWeekId(bookWeekId);
            bookWeekTimeSliceService.insert(entity);
        }
        LOGGER.info("保存后返回的实体类ID={}", bookWeek.getId());
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:bookweek:update")
    public R update(@RequestBody BookWeekEntity bookWeek) {
        ValidatorUtils.validateEntity(bookWeek);
        Long userId = ShiroUtils.getUserId();
        for (BookWeekTimeSliceEntity slice : bookWeek.getSlices()) {
            if (null == slice.getId()) {
                slice.setCreateBy(userId);
                slice.setLastModifyBy(userId);
                slice.setWeekId(bookWeek.getId());
                bookWeekTimeSliceService.insert(slice);
            } else {
                bookWeekTimeSliceService.updateAllColumnById(slice);
            }
        }
        bookWeek.setLastModifyBy(userId);
        bookWeekService.updateAllColumnById(bookWeek);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("book:bookweek:delete")
    public R delete(@RequestBody Integer[] ids) {
        bookWeekService.deleteBatchIds(Arrays.asList(ids));
        Map<String, Object> stringObjectMap = Maps.newHashMap();
        for (Integer id : ids) {
            stringObjectMap.put("week_id", id);
            bookWeekTimeSliceService.deleteByMap(stringObjectMap);
        }
        return R.ok();
    }

    @RequestMapping("/all")
    @RequiresPermissions("book:bookweek:list")
    public R all() {
        List<BookWeekEntity> weeks = bookWeekService.queryAll();
        return R.ok().put("weeks", weeks);
    }
}
