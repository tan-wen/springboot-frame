package com.aoyang.modules.book.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class BookPatientDetail implements Serializable {

    /**
     *就诊患者信息
     */
    private Treatment treatment;


    /**
     * 检查申请单列表
     */
    private List<BookApp> bookApps;

}
