package org.yanzx.core.extend.sap.spring.schema.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.w3c.dom.Element;
import org.yanzx.core.extend.sap.jco.queue.task.JCoContextInitializeTask;
import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.spring.annotation.JCoBeanScan;
import org.yanzx.core.extend.sap.spring.beans.JCoBeanNameCache;
import org.yanzx.core.extend.sap.spring.factory.parser.JCoBeanScanAnnotationParser;
import org.yanzx.core.extend.sap.spring.factory.support.JCoAnnotationBeanNameGenerator;
import org.yanzx.core.spring.util.AnnotationUtils;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/29 0:19
 */
public class InitializeTaskScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String CLASS_ATTRIBUTE = "class";

    /* ============================================================================================================= */

    /* _beanNameGenerator */
    private final BeanNameGenerator _beanNameGenerator = new JCoAnnotationBeanNameGenerator();

    @Override
    public BeanDefinition parse(Element _element, ParserContext _parserContext) {
        try {

            /* Get bean class. */
            Class<?> _beanType = Class.forName(_element.getAttribute(CLASS_ATTRIBUTE));

            if (!JCoContextInitializeTask.class.isAssignableFrom(_beanType))
                throw new JCoBeanNotOfRequiredTypeSemaphore("", JCoContextInitializeTask.class, _beanType); /* Type check. */

            /* Create parser. */
            final Environment _env = _parserContext.getReaderContext().getEnvironment();
            JCoBeanScanAnnotationParser _parser = new JCoBeanScanAnnotationParser(_env, _parserContext.getRegistry());

            /* Construct JCoBeanScan. */
            AnnotationAttributes _jcoBeanScan = new AnnotationAttributes(JCoBeanScan.class);
            AnnotationUtils.registerDefaultValues(_jcoBeanScan);

            /* Do parse. */
            _parser.parse(_jcoBeanScan, this.getClass().getName());

            /* Registry task. */
            BeanDefinition _candidate = BeanDefinitionBuilder.genericBeanDefinition(_beanType)
                    .setLazyInit(true).getBeanDefinition();
            String _beanName =  _beanNameGenerator.generateBeanName(_candidate, _parserContext.getRegistry());
            BeanDefinitionHolder _definitionHolder = new BeanDefinitionHolder(_candidate, _beanName);
            BeanDefinitionReaderUtils.registerBeanDefinition(_definitionHolder, _parserContext.getRegistry());
            /* Cache beanName. */
            JCoBeanNameCache.cache(_definitionHolder.getBeanName());

            return null;

        }catch (Exception _ex) { throw new JCoServerStartOnErrorSemaphore("JCoServer start on error.", _ex); }
    }
}
