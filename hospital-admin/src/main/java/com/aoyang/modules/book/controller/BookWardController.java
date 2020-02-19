package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.webservice.inpatient.Ward;
import com.aoyang.modules.book.webservice.inpatient.WardWs;
import com.aoyang.modules.book.webservice.wardment.Wardment;
import com.aoyang.modules.book.webservice.wardment.WardmentWs;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-12-30 10:34:48
 */
@RestController
@RequestMapping("book/ward")
public class BookWardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookWardController.class);


    /**
     * 根据账号ID获取病区信息清单
     */
    @RequestMapping("/getWardList")
    public R getWardList() {
        List<Ward> wardList = new ArrayList<>();
        String userId = ShiroUtils.getUserEntity().getUsername();
        Long id = ShiroUtils.getUserEntity().getUserId();
        if (id == Constant.SUPER_ADMIN) {
            List<Wardment> WardmentList = WardmentWs.getWardmentList();
            for (Wardment wardment : WardmentList) {
                Ward ward = new Ward();
                ward.setWardId(wardment.getWardId());
                ward.setWardDesc(wardment.getWardCode());
                wardList.add(ward);
            }
        } else {
            // String userId = "1385";
            wardList = WardWs.getWardList(userId);
        }
        return R.ok().put("wardList", wardList);
    }
}
