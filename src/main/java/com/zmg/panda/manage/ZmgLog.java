package com.zmg.panda.manage;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZmgLog {

    String methodContent();
}
