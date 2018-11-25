package com.xuebling.newpoetryspread.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解
public class CustomAnnotation {
    //用于跳过验证
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PassToken {
        boolean required() default true;
    }
    //需登陆才能操作
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NeedToken {
        boolean required() default true;
    }
}
