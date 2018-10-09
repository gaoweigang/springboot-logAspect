package com.gwg.user.config.log;


import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiFlow {
	FlowType value() default FlowType.RECEIPT;//默认代扣
}
