package io.renren.common.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 配置公共字段自动填充功能  @TableField(..fill = FieldFill.INSERT)
 *
 * @author went
 * @email went@aoyang.com
 * @date 2019-10-10 13:27:32
 */
@Component
public class MetaObjectHandlerConfig extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        setFieldValByName("createTime", now, metaObject);
        setFieldValByName("lastModifyTime", now, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("lastModifyTime", new Date(), metaObject);
    }
}
