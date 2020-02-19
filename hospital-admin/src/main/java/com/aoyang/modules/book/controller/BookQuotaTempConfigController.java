package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.entity.BookQuotaTempEntity;
import com.aoyang.modules.book.service.BookItemService;
import com.aoyang.modules.book.service.BookQuotaDetailService;
import com.aoyang.modules.book.service.BookQuotaTempService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.gson.Gson;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysDictService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 号源配置详情临时表
 *
 * @author chenyijie
 */

@RestController
@RequestMapping("book/quotatemp")
public class BookQuotaTempConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookQuotaTempConfigController.class);

    private static final String CONNECTOR = "-";

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private BookQuotaTempService bookQuotaTempService;

    @Autowired
    private BookQuotaDetailService bookQuotaDetailService;

    @Autowired
    private SysDictService sysDictService;


    @RequestMapping("/info")
    public R getQuota(Long detectionId, Long weekId, String checkedDate) {
        LOGGER.info("获取医技科室的项目及时间段，detectionId={},weekId={},checkedDate={}", detectionId, weekId, checkedDate);
        //号源/号源临时表的数据
        DetectionQuota quota = bookItemService.getQuotaTemp(detectionId, weekId, null, "", checkedDate,"");
        return R.ok().put("quota", quota);
    }


    @RequestMapping("/todayInfo")
    public R getQuotaToday(Long detectionId) {
        LOGGER.info("获取今日医技科室的项目及时间段，detectionId={}", detectionId);
        //获取今天日期
        LocalDate localDate = LocalDate.now();
        String checkedDate = localDate.format(DateTimeFormatter.ISO_DATE);//yyyy-mm-dd
        //获取星期几
        int week = localDate.getDayOfWeek().getValue();
        String[] weekArray = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String weekName = weekArray[week];//demo:星期三
        //获取weekId
        SysDictEntity sysDictEntity = new SysDictEntity();
        sysDictEntity.setValue(weekName);
        SysDictEntity result = sysDictService.selectOne(new Wrapper<SysDictEntity>() {
            @Override
            public String getSqlSegment() {
                return "and value='" + sysDictEntity.getValue() + "'";
            }
        });
        //获取主键weekId
        Long weekId = result.getId();
        DetectionQuota quota = bookItemService.getQuotaTemp(detectionId, weekId, null, "",checkedDate,"");
        LOGGER.info("医技科室号源详情临时配置组装数据，quota={}", new Gson().toJson(quota));
        return R.ok().put("quota", quota);


    }


    @RequestMapping("/save")
    public R save(@RequestBody BookQuotaTempEntity quotaTempDetail) {
        LOGGER.info("保存临时配额详情：{}", new Gson().toJson(quotaTempDetail));
        Long curUserId = ShiroUtils.getUserId();
        quotaTempDetail.setCreateBy(curUserId);
        quotaTempDetail.setLastModifyBy(curUserId);
        int count = bookQuotaTempService.selectCount(new Wrapper<BookQuotaTempEntity>() {
            @Override
            public String getSqlSegment() {
                return "and full_time='" + quotaTempDetail.getFullTime() + "'"
                        + " and quota_category_id=" + quotaTempDetail.getQuotaCategoryId() +
                        " and book_item_id=" + quotaTempDetail.getBookItemId()
                        + " and time_slice_id=" + quotaTempDetail.getTimeSliceId();
            }
        });
        //查询 如果存在对应记录,更新.如果没有,保存.
        if (count > 0) {
            bookQuotaTempService.update(quotaTempDetail, new Wrapper<BookQuotaTempEntity>() {
                @Override
                public String getSqlSegment() {
                    return "and full_time='" + quotaTempDetail.getFullTime() + "'"
                            + " and quota_category_id=" + quotaTempDetail.getQuotaCategoryId() +
                            " and book_item_id=" + quotaTempDetail.getBookItemId()
                            + " and time_slice_id=" + quotaTempDetail.getTimeSliceId();
                }
            });
        } else {
            bookQuotaTempService.insert(quotaTempDetail);
        }
        return R.ok();
    }
}
