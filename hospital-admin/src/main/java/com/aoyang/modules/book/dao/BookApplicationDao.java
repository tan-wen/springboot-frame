package com.aoyang.modules.book.dao;


import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.book.entity.BookApplicationEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 预约申请单表
 * 
 * @author daixiongyan
 * @email daixy@aoyang.com
 * @date 2019-11-27 10:06:14
 */
public interface BookApplicationDao extends BaseMapper<BookApplicationEntity> {
    BaseMemberEntity getMemberById(Map<String, Object> param);


    List<BookApplicationEntity> getAppNotBindBookRecord(Map<String, Object> param);

    /**
     * 获取正确关联了预约记录的申请单
     * @param param
     * @return
     */
    List<BookApplicationEntity> findBooked(Map<String, Object> param);
}
