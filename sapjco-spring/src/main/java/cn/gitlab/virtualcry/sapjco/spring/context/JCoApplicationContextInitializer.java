package cn.gitlab.virtualcry.sapjco.spring.context;

import cn.gitlab.virtualcry.sapjco.beans.factory.JCoBeanFactoryProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoErrorListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoExceptionListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoStateChangedListener;
import cn.gitlab.virtualcry.sapjco.spring.beans.factory.SpringJCoExtensionFactory;
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

    public static final String BEAN_NAME = "cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation."
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

        // register jco factory
        JCoBeanFactoryProvider.getSingleton().register(
                new SpringJCoExtensionFactory(applicationContext));

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
                    registry, BEAN_NAME, JCoAnnotationBeanPostProcessor.class);

            // register default beans
            registerDefaultListeners(registry);
        }

        private void registerDefaultListeners(BeanDefinitionRegistry registry) {

            BeanRegistrar.registerInfrastructureBean(
                    registry, ERROR_LISTENER_BEAN_NAME, DefaultJCoErrorListener.class);
            BeanRegistrar.registerInfrastructureBean(
                    registry, EXCEPTION_LISTENER_BEAN_NAME, DefaultJCoExceptionListener.class);
            BeanRegistrar.registerInfrastructureBean(
                    registry, STATE_CHANGED_LISTENER_BEAN_NAME, DefaultJCoStateChangedListener.class);
        }
    }

}
