package cn.gitlab.virtualcry.sapjco.spring.context;

import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoErrorListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoExceptionListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoStateChangedListener;
import cn.gitlab.virtualcry.sapjco.spring.beans.factory.SpringExtensionJCoBeanFactory;
import cn.gitlab.virtualcry.sapjco.spring.beans.factory.SpringExtensionJCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation.JCoAnnotationBeanPostProcessor;
import cn.gitlab.virtualcry.sapjco.spring.util.BeanRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * {@link ApplicationContextInitializer} to register JCo factory and beans
 *
 * @author VirtualCry
 */
@Slf4j
public class JCoApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String JCO_BEAN_FACTORY_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation."
            + "internalSpringExtensionJCoBeanFactory";

    public static final String JCO_CONNECTION_FACTORY_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation."
            + "internalSpringExtensionJCoConnectionFactory";

    public static final String JCO_ANNOTATION_PROCESSOR_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation."
            + "internalJCoAnnotationBeanPostProcessor";

    public static final String ERROR_LISTENER_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.server.listener."
            + "internalDefaultJCoErrorListener";

    public static final String EXCEPTION_LISTENER_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.server.listener."
            + "internalDefaultJCoExceptionListener";

    public static final String STATE_CHANGED_LISTENER_BEAN_NAME = "cn.gitlab.virtualcry.sapjco.server.listener."
            + "internalDefaultJCoStateChangedListener";


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (log.isInfoEnabled())
            log.info("Initializing SAP Java Connector");

        // register provider
        JCoDataProvider.registerInEnvironment();

        // add processor
        applicationContext.addBeanFactoryPostProcessor(
                new JCoComponentScanRegistrar());
    }

    private static class JCoComponentScanRegistrar
            implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE;
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                throws BeansException {
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
                throws BeansException {

            // register processor
            BeanRegistrar.registerInfrastructureBean(
                    registry, JCO_ANNOTATION_PROCESSOR_BEAN_NAME, JCoAnnotationBeanPostProcessor.class);

            // register jco factory
            registerDefaultJCoFactories(registry);

            // register default beans
            registerDefaultJCoListeners(registry);
        }

        private void registerDefaultJCoFactories(BeanDefinitionRegistry registry) {
            BeanRegistrar.registerInfrastructureBean(
                    registry, JCO_BEAN_FACTORY_BEAN_NAME, SpringExtensionJCoBeanFactory.class);
            BeanRegistrar.registerInfrastructureBean(
                    registry, JCO_CONNECTION_FACTORY_BEAN_NAME, SpringExtensionJCoConnectionFactory.class);
        }

        private void registerDefaultJCoListeners(BeanDefinitionRegistry registry) {
            BeanRegistrar.registerInfrastructureBean(
                    registry, ERROR_LISTENER_BEAN_NAME, DefaultJCoErrorListener.class);
            BeanRegistrar.registerInfrastructureBean(
                    registry, EXCEPTION_LISTENER_BEAN_NAME, DefaultJCoExceptionListener.class);
            BeanRegistrar.registerInfrastructureBean(
                    registry, STATE_CHANGED_LISTENER_BEAN_NAME, DefaultJCoStateChangedListener.class);
        }
    }

}
