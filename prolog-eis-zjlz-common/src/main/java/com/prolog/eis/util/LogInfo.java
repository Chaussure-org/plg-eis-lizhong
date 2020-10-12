package com.prolog.eis.util;

import org.apache.poi.ss.formula.functions.T;

import java.lang.annotation.*;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-10 14:24
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {
    String desci();
    String direction();
    int type();
}
