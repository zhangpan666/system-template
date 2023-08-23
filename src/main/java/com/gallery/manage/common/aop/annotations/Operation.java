package com.gallery.manage.common.aop.annotations;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Operation {

    /**
     * 模块
     */
    @AliasFor("type")
    byte value() default 1;

    @AliasFor("value")
    byte type() default 1;

    String module() default "";

    String description() default "";
}
