package cn.gitlab.virtualcry.sapjco.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Indicates that an annotated class is a "Sap RFC server listener"
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
public @interface JCoListener {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = JCoComponent.class)
    String value() default "";
}
