package org.yanzx.core.extend.sap.spring.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.yanzx.core.extend.sap.spring.annotation.JCoBeanScan;
import org.yanzx.core.extend.sap.spring.annotation.JCoBeanScans;
import org.yanzx.core.extend.sap.spring.factory.parser.JCoBeanScanAnnotationParser;

import java.util.Set;

import static org.yanzx.core.spring.util.AnnotationUtils.attributesForRepeatable;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/10 13:40
 */
public class JCoConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered, EnvironmentAware {

    private Environment _env;
    public void setEnvironment(Environment _env) {
        this._env = _env;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry _registry) throws BeansException {
        JCoBeanScanAnnotationParser _jcoBeanScanParser = new JCoBeanScanAnnotationParser(_env, _registry);
        for (String _beanName : _registry.getBeanDefinitionNames()) {
            BeanDefinition _beanDefinition = _registry.getBeanDefinition(_beanName);
            if (_beanDefinition instanceof AnnotatedBeanDefinition) {
                Set<AnnotationAttributes> _jcoBeanScans = attributesForRepeatable(((AnnotatedBeanDefinition) _beanDefinition).getMetadata(),
                        JCoBeanScans.class, JCoBeanScan.class);
                for (AnnotationAttributes _jcoBeanScan : _jcoBeanScans) {
                    _jcoBeanScanParser.parse(_jcoBeanScan, _beanDefinition.getBeanClassName());
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory _beanFactory) throws BeansException {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
