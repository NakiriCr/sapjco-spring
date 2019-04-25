package org.yanzx.core.extend.sap.spring.factory.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.yanzx.core.extend.sap.jco.stereotype.JCoBean;
import org.yanzx.core.extend.sap.spring.beans.JCoBeanNameCache;
import org.yanzx.core.extend.sap.spring.factory.support.JCoAnnotationBeanNameGenerator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 10:29
 */
class ClassPathJCoBeanScanner extends ClassPathBeanDefinitionScanner {

    /* _beanNameGenerator */
    private final BeanNameGenerator _beanNameGenerator = new JCoAnnotationBeanNameGenerator();
    /* _registry */
    private final BeanDefinitionRegistry _registry;

    ClassPathJCoBeanScanner(BeanDefinitionRegistry _registry) {
        super(_registry);
        this._registry = _registry;
    }

    /**
     * Scan annotations.
     */
    public void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(JCoBean.class));
    }

    public Set<BeanDefinitionHolder> doScan(String... _basePackages) {

        /* Scan. */
        Set<BeanDefinitionHolder> _holders = super.doScan(_basePackages);

        /*
         * Process beanDefinitions.
         * 1. Remove origin beanDefinition.
         * 2. Registry new beanDefinition.
         * 3. Cache beanName in order to registry bean to JCoBeanFactory.
         */
        _holders = processBeanDefinitions(_holders);

        return _holders;
    }

    /**
     * Process beanDefinitions.
     * 1. Remove origin beanDefinition.
     * 2. Registry new beanDefinition.
     * 3. Cache beanName in order to registry bean to JCoBeanFactory.
     * @param _holders _holders
     *
     * @see org.yanzx.core.extend.sap.spring.context.JCoContextInit
     */
    private Set<BeanDefinitionHolder> processBeanDefinitions(Set<BeanDefinitionHolder> _holders) {
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();
        for (BeanDefinitionHolder _originHolder : _holders) {

            /* Remove origin beanDefinition. */
            _registry.removeBeanDefinition(_originHolder.getBeanName());

            BeanDefinition _candidate = _originHolder.getBeanDefinition();
            _candidate.setLazyInit(true);
            String _beanName =  _beanNameGenerator.generateBeanName(_candidate, _registry);
            if (checkCandidate(_beanName, _candidate)) {

                /* Registry new beanDefinition. */
                BeanDefinitionHolder _definitionHolder = new BeanDefinitionHolder(_candidate, _beanName);
                beanDefinitions.add(_definitionHolder);
                registerBeanDefinition(_definitionHolder, _registry);

                /* Cache beanName. */
                JCoBeanNameCache.cache(_definitionHolder.getBeanName());
            }
        }

        return beanDefinitions;
    }
}
