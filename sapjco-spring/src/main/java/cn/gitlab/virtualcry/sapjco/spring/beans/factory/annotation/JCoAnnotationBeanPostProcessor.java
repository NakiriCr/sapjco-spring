package cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;
import cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent;
import cn.gitlab.virtualcry.sapjco.spring.context.annotation.JCoComponentScan;
import cn.gitlab.virtualcry.sapjco.spring.context.annotation.JCoComponentScans;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static cn.gitlab.virtualcry.sapjco.spring.util.AnnotationUtils.attributesForRepeatable;

/**
 * {@link JCoComponent} Annotation
 * {@link BeanDefinitionRegistryPostProcessor Bean Definition Registry Post Processor}
 *
 * @author VirtualCry
 */
public class JCoAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor,
        PriorityOrdered, EnvironmentAware, ResourceLoaderAware {

    private Environment                                 environment;
    private ResourceLoader                              resourceLoader;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        JCoComponentScanAnnotationParser componentScanParser =
                new JCoComponentScanAnnotationParser(environment, resourceLoader, registry);

        // scan
        Arrays.stream(registry.getBeanDefinitionNames())
                .map(registry::getBeanDefinition)
                .filter(beanDefinition -> beanDefinition instanceof AnnotatedBeanDefinition)
                .map(beanDefinition -> attributesForRepeatable(
                        ((AnnotatedBeanDefinition) beanDefinition).getMetadata(), JCoComponentScans.class, JCoComponentScan.class)
                )
                .flatMap(Set::stream)
                .map(this::getPackagesToScan)
                .forEach(componentScanParser::parse);
    }

    private Set<String> getPackagesToScan(AnnotationAttributes attributes) {
        String[] basePackages = attributes.getStringArray("basePackages");
        Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
        String[] value = attributes.getStringArray("value");

        // appends value array attributes
        Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
        packagesToScan.addAll(Arrays.asList(basePackages));
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }

        return packagesToScan;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
