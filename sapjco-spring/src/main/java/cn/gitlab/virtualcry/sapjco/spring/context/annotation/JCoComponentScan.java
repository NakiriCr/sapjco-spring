package cn.gitlab.virtualcry.sapjco.spring.context.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * JCo Component Scan {@link Annotation},scans the classpath for annotated components that will be auto-registered as
 * Spring beans.
 *
 * @author VirtualCry
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(JCoComponentScans.class)
public @interface JCoComponentScan {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation
     * declarations e.g.: {@code @JCoComponentScan("org.my.pkg")} instead of
     * {@code @JCoComponentScan(basePackages="org.my.pkg")}.
     *
     * @return the base packages to scan
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * Base packages to scan for annotated @JCoComponent classes. {@link #value()} is an
     * alias for (and mutually exclusive with) this attribute.
     * <p>
     * Use {@link #basePackageClasses()} for a type-safe alternative to String-based
     * package names.
     *
     * @return the base packages to scan
     */
    @AliasFor("value")
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages to
     * scan for annotated @Service classes. The package of each class specified will be
     * scanned.
     *
     * @return classes from the base packages to scan
     */
    Class<?>[] basePackageClasses() default {};
}
