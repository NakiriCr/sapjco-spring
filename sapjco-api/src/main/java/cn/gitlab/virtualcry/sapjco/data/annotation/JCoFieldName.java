package cn.gitlab.virtualcry.sapjco.data.annotation;

import java.lang.annotation.*;

/**
 * Indicates that the annotated field is an alias for the BAPI field.
 *
 * @author VirtualCry
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JCoFieldName {

    /**
     * The value may indicate a suggestion for a BAPI field name,
     * @return the suggested jco field name, if any
     */
    String value() default "";
}
