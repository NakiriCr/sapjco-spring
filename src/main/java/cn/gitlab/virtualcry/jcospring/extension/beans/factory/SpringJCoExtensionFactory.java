package cn.gitlab.virtualcry.jcospring.extension.beans.factory;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanAlreadyRegisterSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoSuchJCoBeanSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoUniqueJCoBeanSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.JCoBeanFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SpringJCoExtensionFactory
 */
public class SpringJCoExtensionFactory implements JCoBeanFactory {

    private final ApplicationContext ctx;

    public SpringJCoExtensionFactory(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    @Override
    public <T> void register(String beanName, T bean) throws JCoBeanAlreadyRegisterSemaphore {
        (((DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory()))
                .registerSingleton(beanName, bean);
    }

    @Override
    public boolean containsBean(String beanName) {
        return ctx.containsBean(beanName);
    }

    @Override
    public Object getBean(String beanName) throws NoSuchJCoBeanSemaphore {
        return ctx.getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        return ctx.getBean(beanName, beanType);
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoUniqueJCoBeanSemaphore, NoSuchJCoBeanSemaphore {
        return ctx.getBean(beanType);
    }

    @Override
    public <T> List<T> getBeans(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        return Arrays.stream(getBeanNamesForType(beanType))
                .map(beanName -> getBean(beanName, beanType))
                .collect(Collectors.toList());
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
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
