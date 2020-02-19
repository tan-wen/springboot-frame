package com.aoyang.modules.base.dao;

import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-08 09:58:35
 */
public interface BaseMemberDao extends BaseMapper<BaseMemberEntity> {

    List<BaseMemberEntity> listPage(Page<BaseMemberEntity> page, String keyword);

    List<BaseMemberEntity> selectByPatNo(Map<String, Object> param);
}
