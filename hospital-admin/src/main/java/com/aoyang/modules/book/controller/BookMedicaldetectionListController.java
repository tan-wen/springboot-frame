package com.aoyang.modules.book.controller;

import com.aoyang.modules.book.service.PayStatus;
import com.aoyang.modules.book.service.TreatmentCategory;
import io.renren.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@author chenyijie
 *@Date  2020/2/10 13:51
 */
@RestController
@RequestMapping("book/bookmedicaldetectionlist")
public class BookMedicaldetectionListController {

    /**
     *获取患者来源
     */
    @RequestMapping("/patientsources")
    public R patientSources(){
        return R.ok().put("patientSources",TreatmentCategory.all()).put("payStatusList", PayStatus.all());
    }

}
