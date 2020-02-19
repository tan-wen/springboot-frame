package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.entity.BookQuotaTempEntity;
import com.aoyang.modules.book.service.BookQuotaTempService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 号源配置详情临时表
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-06 14:14:08
 */
@RestController
@RequestMapping("book/bookquotatemp")
public class BookQuotaTempController {
    @Autowired
    private BookQuotaTempService bookQuotaTempService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:bookquotatemp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bookQuotaTempService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:bookquotatemp:info")
    public R info(@PathVariable("id") Long id){
        BookQuotaTempEntity bookQuotaTemp = bookQuotaTempService.selectById(id);

        return R.ok().put("bookQuotaTemp", bookQuotaTemp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:bookquotatemp:save")
    public R save(@RequestBody BookQuotaTempEntity bookQuotaTemp){
        bookQuotaTempService.insert(bookQuotaTemp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:bookquotatemp:update")
    public R update(@RequestBody BookQuotaTempEntity bookQuotaTemp){
        ValidatorUtils.validateEntity(bookQuotaTemp);
        bookQuotaTempService.updateAllColumnById(bookQuotaTemp);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:bookquotatemp:delete")
    public R delete(@RequestBody Long[] ids){
        bookQuotaTempService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
