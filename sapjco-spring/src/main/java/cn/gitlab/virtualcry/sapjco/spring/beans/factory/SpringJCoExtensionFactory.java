package cn.gitlab.virtualcry.sapjco.spring.beans.factory;

import cn.gitlab.virtualcry.sapjco.beans.factory.JCoBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SpringJCoExtensionFactory
 */
public class SpringJCoExtensionFactory implements JCoBeanFactory {

    private final ApplicationContext            ctx;

    public SpringJCoExtensionFactory(ApplicationContext ctx) {
        Assert.notNull(ctx, "ApplicationContext could not be null.");
        this.ctx = ctx;
    }


    @Override
    public <T> void register(String beanName, T bean) {
        (((DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory()))
                .registerSingleton(beanName, bean);
    }

    @Override
    public boolean containsBean(String beanName) {
        return ctx.containsBean(beanName);
    }

    @Override
    public Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        return ctx.getBean(beanName, beanType);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        return ctx.getBean(beanType);
    }

    @Override
    public <T> List<T> getBeans(Class<T> beanType) {
        return Arrays.stream(getBeanNamesForType(beanType))
                .map(beanName -> getBean(beanName, beanType))
                .collect(Collectors.toList());
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanType) {
        return ctx.getBeansOfType(beanType);
    }

    @Override
    public String[] getBeanNamesForType(Class beanType) {
        return ctx.getBeanNamesForType(beanType);
    }

    @Override
    public void unRegister(String beanName) {
        ((DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory())
                .removeBeanDefinition(beanName);
    }

}
