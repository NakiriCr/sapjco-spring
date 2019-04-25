package org.yanzx.core.extend.sap.spring.schema.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.spring.annotation.JCoBeanScan;
import org.yanzx.core.extend.sap.spring.factory.parser.JCoBeanScanAnnotationParser;
import org.yanzx.core.spring.util.AnnotationUtils;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/29 0:19
 */
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    @Override
    public BeanDefinition parse(Element _element, ParserContext _parserContext) {
        try {
            /* Get _basePackage. */
            String _basePackage = _element.getAttribute(BASE_PACKAGE_ATTRIBUTE);
            String[] _basePackages = StringUtils.tokenizeToStringArray(_basePackage,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);

            /* Create parser. */
            final Environment _env = _parserContext.getReaderContext().getEnvironment();
            JCoBeanScanAnnotationParser _parser = new JCoBeanScanAnnotationParser(_env, _parserContext.getRegistry());

            /* Construct JCoBeanScan. */
            AnnotationAttributes _jcoBeanScan = new AnnotationAttributes(JCoBeanScan.class);
            AnnotationUtils.registerDefaultValues(_jcoBeanScan);
            _jcoBeanScan.put("basePackages", _basePackages);

            /* Do parse. */
            _parser.parse(_jcoBeanScan, this.getClass().getName());

            return null;

        }catch (Exception _ex) { throw new JCoServerStartOnErrorSemaphore("JCoServer start on error.", _ex); }
    }
}
