package cn.gitlab.virtualcry.jcospring.extension.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

/**
 * Bean Registrar
 *
 * @author VirtualCry
 */
public class BeanRegistrar {

    /**
     * Register Infrastructure Bean
     *
     * @param beanDefinitionRegistry {@link BeanDefinitionRegistry}
     * @param beanType               the type of bean
     * @param beanName               the name of bean
     */
    public static void registerInfrastructureBean(BeanDefinitionRegistry beanDefinitionRegistry,
                                                  String beanName,
                                                  Class<?> beanType) {

        if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder
                    .rootBeanDefinition(beanType)
                    .setRole(ROLE_INFRASTRUCTURE)
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        }

    }
}
