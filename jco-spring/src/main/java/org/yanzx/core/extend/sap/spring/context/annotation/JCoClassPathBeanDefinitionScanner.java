package org.yanzx.core.extend.sap.spring.context.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.yanzx.core.extend.sap.spring.annotation.JCoComponent;

import java.util.Set;

import static org.springframework.context.annotation.AnnotationConfigUtils.registerAnnotationConfigProcessors;

/**
 * JCo {@link ClassPathBeanDefinitionScanner} that exposes some methods to be public.
 *
 * @author VirtualCry
 * @see #doScan(String...)
 * @see #registerDefaultFilters()
 */
public class JCoClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {


    public JCoClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment,
                                               ResourceLoader resourceLoader) {

        super(registry, useDefaultFilters);

        setEnvironment(environment);

        setResourceLoader(resourceLoader);

        // add include filter
        addIncludeFilter(new AnnotationTypeFilter(JCoComponent.class));

        registerAnnotationConfigProcessors(registry);

    }

    public JCoClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, Environment environment,
                                               ResourceLoader resourceLoader) {

        this(registry, false, environment, resourceLoader);
    }

    public JCoClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }


    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {

        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

        beanDefinitionHolders.forEach(beanDefinitionHolder -> {
            if (logger.isInfoEnabled()) {
                logger.info("The BeanDefinition[" + beanDefinitionHolder.getBeanDefinition() +
                        "] of ServiceBean has been registered with name : " + beanDefinitionHolder.getBeanName());
            }
        });

        return beanDefinitionHolders;

    }

    @Override
    public boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        return super.checkCandidate(beanName, beanDefinition);
    }

}
