package org.yanzx.core.extend.sap.spring.registrar.annotation;

import org.springframework.context.annotation.Import;
import org.yanzx.core.extend.sap.spring.registrar.JCoFrameworkRegistrar;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/10/14 15:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JCoFrameworkRegistrar.class)
public @interface EnableJCoFramework {
}
