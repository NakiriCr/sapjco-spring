package cn.gitlab.virtualcry.jcospring.extension.context.annotation;

import java.lang.annotation.*;

/**
 * Container annotation that aggregates several {@link JCoComponentScan} annotations.
 *
 * <p>Can be used natively, declaring several nested {@link JCoComponentScan} annotations.
 * Can also be used in conjunction with Java 8's support for repeatable annotations,
 * where {@link JCoComponentScan} can simply be declared several times on the same method,
 * implicitly generating this container annotation.
 *
 * @author VirtualCry
 * @see JCoComponentScan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface JCoComponentScans {

    JCoComponentScan[] value();

}
