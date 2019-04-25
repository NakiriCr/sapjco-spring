package org.yanzx.core.extend.sap.spring.registrar;

import org.springframework.context.annotation.Import;
import org.yanzx.core.extend.sap.spring.factory.support.JCoConfigurationClassPostProcessor;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/10/25 14:48
 */
@Import({
        JCoConfigurationClassPostProcessor.class
})
public class JCoFrameworkRegistrar {

}
