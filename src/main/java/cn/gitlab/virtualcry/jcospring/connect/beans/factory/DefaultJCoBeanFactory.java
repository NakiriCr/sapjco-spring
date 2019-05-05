package cn.gitlab.virtualcry.jcospring.connect.beans.factory;

import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanAlreadyRegisterSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoSuchJCoBeanSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoUniqueJCoBeanSemaphore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Default factory of JCoBean
 *
 * @author VirtualCry
 */
public class DefaultJCoBeanFactory implements JCoBeanFactory {

    // cache bean
    private final Map<String, Object> beanCacheHolder = new ConcurrentHashMap<>();

    @Override
    public <T> void register(String beanName, T bean) throws JCoBeanAlreadyRegisterSemaphore {
        if (containsBean(beanName))
            throw new JCoBeanAlreadyRegisterSemaphore(beanName);
        // cache
        beanCacheHolder.put(beanName, bean);
    }

    @Override
    public boolean containsBean(String beanName) {
        if (beanName == null)
            throw new IllegalArgumentException("Bean name must not be null");
        return beanCacheHolder.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) throws NoSuchJCoBeanSemaphore {
        Object bean = beanCacheHolder.get(beanName);
        if (bean == null)
            throw new NoSuchJCoBeanSemaphore(beanName);
        return bean;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        Object bean = getBean(beanName);
        if (!beanType.isInstance(bean))
            throw new JCoBeanNotOfRequiredTypeSemaphore(beanName, beanType, bean.getClass());
        return (T) bean;
    }

    @Override
    public <T> T getBean(Class<T> beanType) throws NoUniqueJCoBeanSemaphore, NoSuchJCoBeanSemaphore {
        String[] beanNames = getBeanNamesForType(beanType);
        if (beanNames.length == 1) {
            return getBean(beanNames[0], beanType);
        }
        else if (beanNames.length > 1) {
            throw new NoUniqueJCoBeanSemaphore(beanType, beanNames);
        }
        else {
            throw new NoSuchJCoBeanSemaphore(beanType);
        }
    }

    @Override
    public <T> List<T> getBeans(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        return Arrays.stream(getBeanNamesForType(beanType))
                .map(beanName -> getBean(beanName, beanType))
                .collect(Collectors.toList());
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        Map<String, T> beans = new HashMap<>();
        Arrays.stream(getBeanNamesForType(beanType)).forEach(beanName -> {
            beans.put(beanName, getBean(beanName, beanType));
        });
        return beans;
    }

    @Override
    public String[] getBeanNamesForType(Class beanType) {
        if (beanType == null)
            throw new IllegalArgumentException("Bean type must not be null");
        return beanCacheHolder.entrySet().stream()
                .filter(entry -> beanType.isInstance(entry.getValue()))
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }

    @Override
    public void unRegister(String beanName) {
        beanCacheHolder.remove(beanName);
    }
}
