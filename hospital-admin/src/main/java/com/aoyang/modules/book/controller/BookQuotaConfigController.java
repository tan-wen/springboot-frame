package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.dto.DetectionQuota;
import com.aoyang.modules.book.entity.BookQuotaDetailEntity;
import com.aoyang.modules.book.service.BookItemService;
import com.aoyang.modules.book.service.BookQuotaDetailService;
import com.google.gson.Gson;
import io.renren.common.utils.R;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book/quota")
public class BookQuotaConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookQuotaConfigController.class);

    private static final String CONNECTOR =  "-";

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private BookQuotaDetailService bookQuotaDetailService;

    @RequestMapping("/info")
    public R getQuota(Long detectionId, Long weekId) {
        LOGGER.info("获取医技科室的项目及时间段，detectionId={},weekId={}", detectionId, weekId);
        DetectionQuota quota = bookItemService.getQuota(detectionId, weekId,null,"");
        return R.ok().put("quota", quota);
    }

    @RequestMapping("/save")
    public R save(@RequestBody BookQuotaDetailEntity quotaDetail) {
        LOGGER.info("保存配额详情：{}", new Gson().toJson(quotaDetail));
        Long curUserId = ShiroUtils.getUserId();
        quotaDetail.setCreateBy(curUserId);
        quotaDetail.setLastModifyBy(curUserId);
        bookQuotaDetailService.insertOrUpdate(quotaDetail);
        return R.ok();
    }
}
