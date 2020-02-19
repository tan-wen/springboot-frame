package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookItemEntity;
import com.aoyang.modules.book.entity.BookItemWeekEntity;
import com.aoyang.modules.book.service.BookItemService;
import com.aoyang.modules.book.service.BookItemWeekService;
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
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-14 13:34:48
 */
@RestController
@RequestMapping("book/bookitem")
public class BookItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookItemController.class);

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private BookItemWeekService bookItemWeekService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:bookitem:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookItemService.listPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("book:bookitem:info")
    public R info(@PathVariable("id") Long id) {
        BookItemEntity bookItem = bookItemService.selectById(id);
        Map<String, Object> para = Maps.newHashMap();
        para.put("book_item_id", id);
        List<BookItemWeekEntity> itemWeeks = bookItemWeekService.selectByMap(para);
        return R.ok().put("bookItem", bookItem).put("itemWeeks", itemWeeks);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("book:bookitem:save")
    public R save(@RequestBody BookItemEntity bookItem) {
        LOGGER.info("【预约项目】保存，{}", new Gson().toJson(bookItem));
        Long curUserId = ShiroUtils.getUserId();
        bookItem.setCreateBy(curUserId);
        bookItem.setLastModifyBy(curUserId);
        bookItemService.insert(bookItem);
        for (BookItemWeekEntity itemWeek : bookItem.getItemWeeks()) {
            itemWeek.setCreateBy(curUserId);
            itemWeek.setLastModifyBy(curUserId);
            itemWeek.setBookItemId(bookItem.getId());
            bookItemWeekService.insert(itemWeek);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("book:bookitem:update")
    public R update(@RequestBody BookItemEntity bookItem) {
        ValidatorUtils.validateEntity(bookItem);
        Long curUserId = ShiroUtils.getUserId();
        bookItem.setLastModifyBy(curUserId);
        bookItemService.updateById(bookItem);
        for (BookItemWeekEntity itemWeek : bookItem.getItemWeeks()) {
            if (null == itemWeek.getId()) {
                itemWeek.setCreateBy(curUserId);
                itemWeek.setLastModifyBy(curUserId);
                itemWeek.setBookItemId(bookItem.getId());
                bookItemWeekService.insert(itemWeek);
            }
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("book:bookitem:delete")
    public R delete(@RequestBody Long[] ids) {
        bookItemService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/all")
    @RequiresPermissions("book:bookitem:list")
    public R all() {
        List<BookItemEntity> bookItems = bookItemService.selectByMap(null);
        return R.ok().put("bookItems", bookItems);
    }

    @RequestMapping("/findByDetectionDeptId")
    public R findByDetectionDeptId(@RequestParam("detectionDeptId") Long detectionDeptId) {
        Map<String, Object> para = Maps.newHashMap();
        para.put("detectionDeptId", detectionDeptId);
        List<BookItemEntity> bookItems = bookItemService.getBookItemListByDetectionDeptId(para);
        return R.ok().put("bookItems", bookItems);
    }

}
