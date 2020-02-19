package com.aoyang.modules.book.task;

import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("taskDemo")
public class TaskDemo {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    public void test(String params) {
        LOGGER.info("我是带参数的test方法，正在被执行，参数为：" + params);
        SysUserEntity user = sysUserService.selectById(1L);
        LOGGER.info(ToStringBuilder.reflectionToString(user));
    }

    public void test2() {
        LOGGER.info("我是不带参数的test2方法，正在被执行");
    }
}
