package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.aoyang.modules.book.service.BookQuotaDetailService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 号源配置详情表
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-11-19 14:11:37
 */
@RestController
@RequestMapping("book/bookquotadetail")
public class BookQuotaDetailController {
    @Autowired
    private BookQuotaDetailService bookQuotaDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:bookquotadetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bookQuotaDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:bookquotadetail:info")
    public R info(@PathVariable("id") Long id){
        BookQuotaDetailEntity bookQuotaDetail = bookQuotaDetailService.selectById(id);

        return R.ok().put("bookQuotaDetail", bookQuotaDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:bookquotadetail:save")
    public R save(@RequestBody BookQuotaDetailEntity bookQuotaDetail){
        bookQuotaDetailService.insert(bookQuotaDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:bookquotadetail:update")
    public R update(@RequestBody BookQuotaDetailEntity bookQuotaDetail){
        ValidatorUtils.validateEntity(bookQuotaDetail);
        bookQuotaDetailService.updateAllColumnById(bookQuotaDetail);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:bookquotadetail:delete")
    public R delete(@RequestBody Long[] ids){
        bookQuotaDetailService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
