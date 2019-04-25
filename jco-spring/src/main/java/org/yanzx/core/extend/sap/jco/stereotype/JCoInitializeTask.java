package org.yanzx.core.extend.sap.jco.stereotype;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/30 1:44
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JCoBean
public @interface JCoInitializeTask {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = JCoBean.class)
    String value() default "";
}
