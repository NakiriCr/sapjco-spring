package cn.gitlab.virtualcry.sapjco.spring.schema;

import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation.JCoComponentScanAnnotationParser;
import cn.gitlab.virtualcry.sapjco.spring.context.annotation.JCoClassPathBeanDefinitionScanner;

/**
 * Parser for the {@code <jco:component-scan/>} element.
 *
 * @author VirtualCry
 * @see JCoComponentScanAnnotationParser
 * @see ComponentScanBeanDefinitionParser
 */
public class JCoComponentScanBeanDefinitionParser extends ComponentScanBeanDefinitionParser {


    @Override
    protected ClassPathBeanDefinitionScanner createScanner(XmlReaderContext readerContext, boolean useDefaultFilters) {

        // use JCoClassPathBeanDefinitionScanner
        return new JCoClassPathBeanDefinitionScanner(
                readerContext.getRegistry(), readerContext.getEnvironment(), readerContext.getResourceLoader());

    }
}
