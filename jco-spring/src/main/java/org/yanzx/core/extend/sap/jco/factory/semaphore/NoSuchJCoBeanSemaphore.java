package org.yanzx.core.extend.sap.jco.factory.semaphore;

import org.springframework.beans.BeansException;
import org.springframework.util.ClassUtils;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 12:50
 */
public class NoSuchJCoBeanSemaphore extends BeansException {

    private String beanName;

    /** Required type of the missing bean */
    private Class<?> beanType;


    /**
     * Create a new {@code NoSuchJCoBeanSemaphore}.
     * @param name the name of the missing bean
     */
    public NoSuchJCoBeanSemaphore(String name) {
        super("No bean named '" + name + "' is defined");
        this.beanName = name;
    }

    /**
     * Create a new {@code NoSuchJCoBeanSemaphore}.
     * @param name the name of the missing bean
     * @param message detailed message describing the problem
     */
    public NoSuchJCoBeanSemaphore(String name, String message) {
        super("No bean named '" + name + "' is defined: " + message);
        this.beanName = name;
    }

    /**
     * Create a new {@code NoSuchJCoBeanSemaphore}.
     * @param type required type of the missing bean
     */
    public NoSuchJCoBeanSemaphore(Class<?> type) {
        super("No qualifying bean of type [" + type.getName() + "] is defined");
        this.beanType = type;
    }

    /**
     * Create a new {@code NoSuchJCoBeanSemaphore}.
     * @param type required type of the missing bean
     * @param message detailed message describing the problem
     */
    public NoSuchJCoBeanSemaphore(Class<?> type, String message) {
        super("No qualifying bean of type [" + ClassUtils.getQualifiedName(type) + "] is defined: " + message);
        this.beanType = type;
    }


    /**
     * Return the name of the missing bean, if it was a lookup <em>by name</em> that failed.
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Return the required type of the missing bean, if it was a lookup <em>by type</em> that failed.
     */
    public Class<?> getBeanType() {
        return this.beanType;
    }

    /**
     * Return the number of beans found when only one matching bean was expected.
     * For a regular NoSuchBeanDefinitionException, this will always be 0.
     * @see NoUniqueBeanDefinitionException
     */
    public int getNumberOfBeansFound() {
        return 0;
    }
}
