package com.aoyang.modules.book.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import com.aoyang.modules.book.entity.BookQuotaTempEntity;

import java.util.Map;

/**
 * 号源配置详情临时表
 *
 * @author cyj
 * @email chenYJ02@aoyang.com
 * @date 2020-01-06 14:14:08
 */
public interface BookQuotaTempService extends IService<BookQuotaTempEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

