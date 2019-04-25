package org.yanzx.core.extend.sap.spring.factory.parser;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.yanzx.core.extend.sap.jco.beans.JCoServerConfig.DEFAULT_CONTEXT_INIT_BASE_PACKAGES;
import static org.yanzx.core.extend.sap.jco.beans.JCoServerConfig.DEFAULT_JCO_LISTENER_BASE_PACKAGES;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/10 14:22
 */
public class JCoBeanScanAnnotationParser {

    /* _env */
    private final Environment _env;
    /* _registry */
    private final BeanDefinitionRegistry _registry;

    public JCoBeanScanAnnotationParser(Environment _env, BeanDefinitionRegistry _registry) {
        this._env = _env;
        this._registry = _registry;
    }

    public Set<BeanDefinitionHolder> parse(AnnotationAttributes _jcoBeanScan, final String _declaringClass) {

        /* Create scanner. */
        ClassPathJCoBeanScanner _scanner = new ClassPathJCoBeanScanner(_registry);

        Set<String> _basePackages = new LinkedHashSet<>();
        for (String _pkg : _jcoBeanScan.getStringArray("basePackages")) {
            String[] _tokenized = StringUtils.tokenizeToStringArray(_env.resolvePlaceholders(_pkg),
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            _basePackages.addAll(Arrays.asList(_tokenized));
        }
        for (Class<?> _clazz : _jcoBeanScan.getClassArray("basePackageClasses")) {
            _basePackages.add(ClassUtils.getPackageName(_clazz));
        }
        if (_basePackages.isEmpty()) {
            _basePackages.add(ClassUtils.getPackageName(_declaringClass));
        }

        /* Add default listener pkg. */
        _basePackages.add(DEFAULT_JCO_LISTENER_BASE_PACKAGES);
        /* Add default contextInit pkg. */
        _basePackages.add(DEFAULT_CONTEXT_INIT_BASE_PACKAGES);

        _scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {
            @Override
            protected boolean matchClassName(String className) {
                return _declaringClass.equals(className);
            }
        });

        return _scanner.doScan(StringUtils.toStringArray(_basePackages));
    }
}
