package cn.gitlab.virtualcry.sapjco.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * {@link org.springframework.beans.factory.xml.NamespaceHandler}
 * for the '{@code context}' namespace.
 *
 * @author VirtualCry
 */
public class JCoNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("component-scan", new JCoComponentScanBeanDefinitionParser());
    }
}
