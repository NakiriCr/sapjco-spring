package org.yanzx.core.extend.sap.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.yanzx.core.extend.sap.spring.schema.parser.ComponentScanBeanDefinitionParser;
import org.yanzx.core.extend.sap.spring.schema.parser.InitializeTaskScanBeanDefinitionParser;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/29 0:15
 */
public class JCoNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
        registerBeanDefinitionParser("initialize-task", new InitializeTaskScanBeanDefinitionParser());
    }
}
