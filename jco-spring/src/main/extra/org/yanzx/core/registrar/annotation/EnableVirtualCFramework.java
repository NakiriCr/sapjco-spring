package org.yanzx.core.registrar.annotation;

import org.springframework.context.annotation.Import;
import org.yanzx.core.registrar.VirtualCFrameworkRegistrar;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/10/21 15:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(VirtualCFrameworkRegistrar.class)
public @interface EnableVirtualCFramework {
}
