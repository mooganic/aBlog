package com.wuminggao.blog.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wuminggao
 * @create 2020-08-02-下午4:20
 */
@Slf4j
@Component//需要将处理器交给Spring管理
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("nsert fill");
        //MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        //自动填充字段
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("start update fill");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
