package org.yanzx.core.extend.sap.jco.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.yanzx.core.extend.sap.jco.stereotype.JCoBean;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 16:49
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JCoBean
public @interface JCoExceptionListener {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a JCo bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = JCoBean.class)
    String value() default "";
}
