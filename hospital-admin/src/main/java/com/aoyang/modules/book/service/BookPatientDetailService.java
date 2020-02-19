package com.aoyang.modules.book.service;

import com.aoyang.modules.book.dto.BookPatientDetail;

/**
 * 病人预约详情接口
 *
 * @author daixy
 * @email daixy@aoyang.com
 * @date 2020-1-19 10:16:18
 */
public interface BookPatientDetailService {



    /**
     * 获取预约病人详情信息
     * @param AdmNo
     * @return
     */
    BookPatientDetail getPatientDetail(String AdmNo);


}

