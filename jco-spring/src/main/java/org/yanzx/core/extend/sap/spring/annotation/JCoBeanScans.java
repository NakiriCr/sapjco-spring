package org.yanzx.core.extend.sap.spring.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/10 13:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface JCoBeanScans {

    JCoBeanScan[] value();
}
