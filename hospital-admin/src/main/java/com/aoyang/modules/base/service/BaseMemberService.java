package com.aoyang.modules.base.service;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-08 09:58:35
 */
public interface BaseMemberService extends IService<BaseMemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listPage(Map<String, Object> params);

    List<BaseMemberEntity> selectByPatNo(String patNo);


}

