package cn.gitlab.virtualcry.sapjco.spring.context.annotation;

import cn.gitlab.virtualcry.sapjco.spring.context.JCoApplicationContextInitializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * EnableJCoRegister
 */
public class EnableJCoRegister implements ApplicationContextAware {

    private final ApplicationContextInitializer<ConfigurableApplicationContext> initializer =
            new JCoApplicationContextInitializer();


    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        initializer.initialize((ConfigurableApplicationContext) ctx);
    }
}
