package org.yanzx.core.extend.sap.spring.annotation;

import org.springframework.core.annotation.AliasFor;
import org.yanzx.core.extend.sap.spring.factory.parser.JCoBeanScanAnnotationParser;

import java.lang.annotation.*;

/**
 * Description:
 * {@link JCoBeanScanAnnotationParser}.
 *
 * @author VirtualCry
 * @date 2018/11/18 23:24
 *
 * @see JCoBeanScanAnnotationParser
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Repeatable(JCoBeanScans.class)
public @interface JCoBeanScan {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise
     * annotation declarations e.g.:
     * {@code @JCoBeanScan("org.my.pkg")} instead of {@code
     * @JCoBeanScan(basePackages= "org.my.pkg"})}.
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * Base packages to scan for @IEntityStatement annotation.
     */
    @AliasFor("value")
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages
     * to scan for annotated components. The package of each class specified will be scanned.
     * <p>Consider creating a special no-op marker class or interface in each package
     * that serves no purpose other than being referenced by this attribute.
     */
    Class<?>[] basePackageClasses() default {};
}
