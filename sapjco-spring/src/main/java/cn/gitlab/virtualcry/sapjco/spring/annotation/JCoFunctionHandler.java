package cn.gitlab.virtualcry.sapjco.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Indicates that an annotated class is a "Sap RFC function handler"
 *
 * <p>This annotation serves as a specialization of {@link JCoComponent @JCoComponent},
 * allowing for implementation classes to be autodetected through classpath scanning.
 *
 * @author VirtualCry
 * @see JCoComponent
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JCoComponent
public @interface JCoFunctionHandler {

    /**
     * The value may indicate a suggestion for a sap RFC function name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the sap RFC function name
     */
    @AliasFor(annotation = JCoComponent.class)
    String value() default "";
}
