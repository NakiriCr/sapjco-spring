package cn.gitlab.virtualcry.sapjco.beans.factory;

import java.util.List;
import java.util.Map;

/**
 *  An abstract factory to get bean.
 *
 * @author VirtualCry
 */
public interface JCoBeanFactory {

    /**
     * Register bean in cache.
     * @param beanName  The {@literal beanName} to be used for registering
     * @param bean The {@literal bean} to be used for caching
     */
    <T> void register(String beanName, T bean);


    /**
     * Check is already register in factory.
     * @param beanName The {@literal beanName} to be used for matching
     * @return {@literal check result}
     */
    boolean containsBean(String beanName);


    /**
     * Get bean.
     * @param beanName The {@literal beanName} to be used for matching
     * @return {@literal bean of matching name.}
     */
    Object getBean(String beanName);


    /**
     * Get bean.
     * @param beanName The {@literal beanName} to be used for matching
     * @param beanType The {@literal beanType} to be used for matching
     * @return {@literal bean of matching name and type.}
     */
    <T> T getBean(String beanName, Class<T> beanType);


    /**
     * Get bean.
     * @param beanType The {@literal beanType} to be used for matching
     * @return {@literal bean of matching type.}
     *
     */
    <T> T getBean(Class<T> beanType);


    /**
     * Get beans as list.
     * @param beanType The {@literal beanType} to be used for matching
     * @return {@literal bean list of matching type.}
     */
    <T> List<T> getBeans(Class<T> beanType);


    /**
     * Get beans as map.
     * @param beanType The {@literal beanType} to be used for matching
     * @return {@literal bean map of matching type.}
     */
    <T> Map<String, T> getBeansOfType(Class<T> beanType);


    /**
     * Get bean names.
     * @param beanType The {@literal beanType} to be used for matching
     * @return {@literal bean name list.}}
     */
    String[] getBeanNamesForType(Class beanType);


    /**
     * Un register.
     * @param beanName The {@literal beanType} to be used for un register.
     */
    void unRegister(String beanName);
}
