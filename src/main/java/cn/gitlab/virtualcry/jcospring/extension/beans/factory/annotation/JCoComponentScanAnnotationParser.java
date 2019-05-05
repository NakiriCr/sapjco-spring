package cn.gitlab.virtualcry.jcospring.extension.beans.factory.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import cn.gitlab.virtualcry.jcospring.extension.annotation.JCoComponent;
import cn.gitlab.virtualcry.jcospring.extension.context.annotation.JCoClassPathBeanDefinitionScanner;
import cn.gitlab.virtualcry.jcospring.extension.context.annotation.JCoComponentScan;
import cn.gitlab.virtualcry.jcospring.extension.schema.JCoComponentScanBeanDefinitionParser;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.context.annotation.AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR;

/**
 * Parser for the @{@link JCoComponentScan} annotation.
 *
 * @author VirtualCry
 * @see JCoClassPathBeanDefinitionScanner#scan(String...)
 * @see JCoComponentScanBeanDefinitionParser
 */
public class JCoComponentScanAnnotationParser {

    private static final Log logger = LogFactory
            .getLog(JCoComponentScanAnnotationParser.class);

    private Environment environment;

    private ResourceLoader resourceLoader;

    private final BeanNameGenerator beanNameGenerator;

    private final BeanDefinitionRegistry registry;

    public JCoComponentScanAnnotationParser(Environment environment, ResourceLoader resourceLoader,
                                            BeanDefinitionRegistry registry) {

        this.resourceLoader = resourceLoader;
        this.environment = environment;
        this.beanNameGenerator = resolveBeanNameGenerator(registry);
        this.registry = registry;
    }

    public Set<BeanDefinitionHolder> parse(Set<String> packagesToScan) {

        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

        if (CollectionUtils.isEmpty(resolvedPackagesToScan)) {

            return new LinkedHashSet<>(0);
        }
        else {
            return registerJCoBeans(resolvedPackagesToScan, registry);
        }
    }

    /**
     * Registers Beans whose classes was annotated {@link JCoComponent}
     *
     * @param packagesToScan The base packages to scan
     * @param registry       {@link BeanDefinitionRegistry}
     */
    private Set<BeanDefinitionHolder> registerJCoBeans(Set<String> packagesToScan, BeanDefinitionRegistry registry) {

        JCoClassPathBeanDefinitionScanner scanner =
                new JCoClassPathBeanDefinitionScanner(registry, environment, resourceLoader);

        scanner.setBeanNameGenerator(beanNameGenerator);

        // registers @JCoComponent bean
        return packagesToScan.stream()
                .map(scanner::doScan)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * It'd better to use BeanNameGenerator instance that should reference
     * thus it maybe a potential problem on bean name generation.
     *
     * @param registry {@link BeanDefinitionRegistry}
     * @return {@link BeanNameGenerator} instance
     * @see SingletonBeanRegistry
     * @see AnnotationConfigUtils#CONFIGURATION_BEAN_NAME_GENERATOR
     * @see ConfigurationClassPostProcessor#processConfigBeanDefinitions
     */
    private BeanNameGenerator resolveBeanNameGenerator(BeanDefinitionRegistry registry) {

        BeanNameGenerator beanNameGenerator = null;

        if (registry instanceof SingletonBeanRegistry) {
            SingletonBeanRegistry singletonBeanRegistry = SingletonBeanRegistry.class.cast(registry);
            beanNameGenerator = (BeanNameGenerator) singletonBeanRegistry.getSingleton(CONFIGURATION_BEAN_NAME_GENERATOR);
        }

        if (beanNameGenerator == null) {

            if (logger.isDebugEnabled()) {

                logger.debug("BeanNameGenerator bean can't be found in BeanFactory with name ["
                        + CONFIGURATION_BEAN_NAME_GENERATOR + "]");
                logger.debug("BeanNameGenerator will be a instance of " +
                        JCoAnnotationBeanNameGenerator.class.getName() +
                        " , it maybe a potential problem on bean name generation.");
            }

            beanNameGenerator = new JCoAnnotationBeanNameGenerator();

        }

        return beanNameGenerator;

    }

    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        return packagesToScan.stream()
                .filter(StringUtils::hasText)
                .map(packageToScan -> environment.resolvePlaceholders(packageToScan.trim()))
                .collect(Collectors.toSet());
    }

}
