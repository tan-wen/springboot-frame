package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.dto.BookRecordDetail;
import com.aoyang.modules.book.dto.BookRecordItem;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.aoyang.modules.book.entity.BookRecordEntity;
import com.aoyang.modules.book.service.BookApplicationService;
import com.aoyang.modules.book.service.BookRecordService;
import com.aoyang.modules.book.service.BookTreatmentService;
import com.google.common.collect.Lists;
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
 * 预约记录表
 *
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
@RestController
@RequestMapping("book/bookrecord")
public class BookRecordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookRecordController.class);

    @Autowired
    private BookRecordService bookRecordService;

    @Autowired
    private BookApplicationService bookApplicationService;

    @Autowired
    private BookTreatmentService bookTreatmentService;



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:bookrecord:list")
    public R list(@RequestParam Map<String, Object> params){
        LOGGER.info("【预约记录】获取列表数据，参数：{}", new Gson().toJson(params));
        PageUtils page = bookRecordService.queryPage(params);
        LOGGER.info("【预约记录】获取列表数据：{}", new Gson().toJson(page));
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:bookrecord:info")
    public R info(@PathVariable("id") Long id){
        LOGGER.info("【预约记录】获取预约记录信息，参数：{}", id);
        BookRecordEntity bookRecord = bookRecordService.selectById(id);
        LOGGER.info("【预约记录】获取预约记录信息：{}", new Gson().toJson(bookRecord));
        return R.ok().put("bookRecord", bookRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:bookrecord:save")
    public R save(@RequestBody BookRecordEntity bookRecord){
        LOGGER.info("【预约记录】保存，参数：{}", new Gson().toJson(bookRecord));
        Long userId = ShiroUtils.getUserId();
        bookRecord.setCreateBy(userId);
        bookRecord.setLastModifyBy(userId);
        bookRecordService.insert(bookRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:bookrecord:update")
    public R update(@RequestBody BookRecordEntity bookRecord){
        LOGGER.info("【预约记录】更新，参数：{}", new Gson().toJson(bookRecord));
        ValidatorUtils.validateEntity(bookRecord);
        bookRecord.setLastModifyBy(ShiroUtils.getUserId());
        bookRecordService.updateAllColumnById(bookRecord);//全部更新
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:bookrecord:delete")
    public R delete(@RequestBody Long[] ids){
        LOGGER.info("【预约记录】删除，参数：{}", new Gson().toJson(ids));
        bookRecordService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 根据 book_record 的主键删除
     * @param recordId
     * @return
     */

    @RequestMapping("/deleteById/{recordId}")
    public R deleteById(@PathVariable("recordId") Long recordId) {
        LOGGER.info("【book_record】根据主键删除，recordId：{}", recordId);
        //  逻辑删除 book_record 中对应记录
        bookRecordService.deleteById(recordId);
        LOGGER.info("删除recordId:{} 记录成功", recordId);
        // 逻辑删除与与book_record关联的book_application 记录
        List<BookApplicationEntity> bookApplicationEntities= bookApplicationService.getByRecordId(recordId);
        LOGGER.info("与book_record关联的bookApplicationEntities:{}",new Gson().toJson(bookApplicationEntities));
        for(BookApplicationEntity bookApplicationEntity:bookApplicationEntities){
            Long bookAppId = bookApplicationEntity.getId();
            bookApplicationService.deleteById(bookAppId);
            LOGGER.info("删除成功bookApplicationEntity:{} ",new Gson().toJson(bookApplicationEntity));
        }
        return R.ok();
    }


    /**
     *获取用户预约记录
     * @param adminNo
     * @return
     */
    @RequestMapping("/getBookRecord/{adminNo}")
    public R getBookRecord(@PathVariable("adminNo") String adminNo) {
        LOGGER.info("查询挂号预约记录，adminNo={}", adminNo);
        //查找所有的预约记录
        BookRecordDetail bookRecordDetail =bookTreatmentService.getBookRecordByAdmNo(adminNo);
        List<BookRecordItem> bookRecordItemList = Lists.newArrayList();
        if(null!=bookRecordDetail&&null!=bookRecordDetail.getId()){
            bookRecordItemList = bookRecordService.getBookRecordItem(bookRecordDetail);
        }
        LOGGER.info("查询挂号预约记录，bookRecordItemList={}", new Gson().toJson(bookRecordItemList));
        return R.ok().put("bookRecordItemList", bookRecordItemList);
    }



}
