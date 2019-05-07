package cn.gitlab.virtualcry.jcospring.extension.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.JCoBeanFactoryProvider;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoDataProvider;
import cn.gitlab.virtualcry.jcospring.connect.server.listener.DefaultJCoErrorListener;
import cn.gitlab.virtualcry.jcospring.connect.server.listener.DefaultJCoExceptionListener;
import cn.gitlab.virtualcry.jcospring.connect.server.listener.DefaultJCoStateChangedListener;
import cn.gitlab.virtualcry.jcospring.extension.beans.factory.annotation.JCoAnnotationBeanPostProcessor;
import cn.gitlab.virtualcry.jcospring.extension.beans.factory.SpringJCoExtensionFactory;
import cn.gitlab.virtualcry.jcospring.extension.util.BeanRegistrar;

/**
 * {@link ApplicationContextInitializer} to register JCo factory & beans
 *
 * @author VirtualCry
 */
public class JCoApplicationContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Log logger =
            LogFactory.getLog(JCoApplicationContextInitializer.class);


    public static final String BEAN_NAME = "cn.gitlab.virtualcry.jcospring.extension.beans.factory.annotation."
            + "internalJCoAnnotationBeanPostProcessor";

    public static final String ERROR_LISTENER_BEAN_NAME = "org.yanzx.core.extend.sap.jco.server.listener."
            + "internalDefaultJCoErrorListener";

    public static final String EXCEPTION_LISTENER_BEAN_NAME = "org.yanzx.core.extend.sap.jco.server.listener."
            + "internalDefaultJCoExceptionListener";

    public static final String STATE_CHANGED_LISTENER_BEAN_NAME = "org.yanzx.core.extend.sap.jco.server.listener."
            + "internalDefaultJCoStateChangedListener";


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (logger.isInfoEnabled())
            logger.info("Initializing JCo");

        // register provider
        JCoDataProvider.registerInEnvironment();

        // register jco factory
        JCoBeanFactoryProvider.register(
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
