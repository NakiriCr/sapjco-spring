package cn.gitlab.virtualcry.jcospring.extension.context.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Enables JCo components as Spring Beans, equals
 * {@link JCoComponentScan}.
 * <p>
 * Note : {@link EnableJCo} must base on Spring Framework 4.2 and above
 *
 * @author VirtualCry
 * @see JCoComponentScan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JCoComponentScan
@Deprecated
public @interface EnableJCo {

    /**
     * Base packages to scan for annotated @Service classes.
     * <p>
     * Use {@link #scanBasePackageClasses()} for a type-safe alternative to String-based
     * package names.
     *
     * @return the base packages to scan
     * @see JCoComponentScan#basePackages()
     */
    @AliasFor(annotation = JCoComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    /**
     * Type-safe alternative to {@link #scanBasePackages()} for specifying the packages to
     * scan for annotated @Service classes. The package of each class specified will be
     * scanned.
     *
     * @return classes from the base packages to scan
     * @see JCoComponentScan#basePackageClasses
     */
    @AliasFor(annotation = JCoComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};
}
