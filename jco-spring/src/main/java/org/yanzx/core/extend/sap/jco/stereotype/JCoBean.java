package org.yanzx.core.extend.sap.jco.stereotype;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 11:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JCoBean {

    String value() default "";
}
