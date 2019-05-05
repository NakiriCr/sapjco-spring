package cn.gitlab.virtualcry.jcospring.connect.beans.factory;

import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanAlreadyRegisterSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoSuchJCoBeanSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.NoUniqueJCoBeanSemaphore;

import java.util.List;
import java.util.Map;

/**
 *  Factory for JCoBean
 *
 * @author VirtualCry
 */
public interface JCoBeanFactory {

    /**
     * register
     * @param beanName name
     * @param bean bean
     */
    <T> void register(String beanName, T bean) throws JCoBeanAlreadyRegisterSemaphore;


    /**
     * check is already register in factory
     * @param beanName name
     */
    boolean containsBean(String beanName);


    /**
     * get bean
     * @param beanName name
     */
    Object getBean(String beanName) throws NoSuchJCoBeanSemaphore;


    /**
     * get bean
     * @param beanName name
     * @param beanType type
     */
    <T> T getBean(String beanName, Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore;


    /**
     * get bean
     * @param beanType type
     *
     */
    <T> T getBean(Class<T> beanType) throws NoUniqueJCoBeanSemaphore, NoSuchJCoBeanSemaphore;


    /**
     * get beans
     * @param beanType type
     */
    <T> List<T> getBeans(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore;


    /**
     * get beans as map
     * @param beanType type
     */
    <T> Map<String, T> getBeansOfType(Class<T> beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore;


    /**
     * get bean name
     * @param beanType type
     */
    String[] getBeanNamesForType(Class beanType);


    /**
     * un register
     * @param beanName name
     */
    void unRegister(String beanName);
}
