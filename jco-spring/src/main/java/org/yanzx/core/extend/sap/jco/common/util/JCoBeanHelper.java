package org.yanzx.core.extend.sap.jco.common.util;

import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanCreationSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.NoSuchJCoBeanSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.NoUniqueJCoBeanSemaphore;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;

import java.util.List;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/17 11:57
 */
public class JCoBeanHelper {

    private static JCoBeanFactory _factory = new JCoBeanFactory();

    public static void setJCoBeanFactory(JCoBeanFactory _beanFactory) {
        _factory = _beanFactory;
    }
    public static JCoBeanFactory getJCoBeanFactory() {
        return _factory;
    }

    /* ============================================================================================================= */

    /**
     * Registry.
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public static <T> void registry(T _bean) throws JCoBeanCreationSemaphore {
        _factory.registry(_bean);
    }

    /**
     * Registry.
     * @param _beanName _beanName
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public static <T> void registry(String _beanName, T _bean) throws JCoBeanCreationSemaphore {
        _factory.registry(_beanName, _bean);
    }

    /* ============================================================================================================= */

    /**
     * Check is already registry in factory
     * @param _beanName _beanName
     * @return boolean
     */
    public static boolean isRegistry(String _beanName) {
        return _factory.isRegistry(_beanName);
    }

    /* ============================================================================================================= */

    /**
     * Get bean.
     * @param _beanName _beanName
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     * @return Object
     */
    public static Object getBean(String _beanName) throws NoSuchJCoBeanSemaphore {
        return _factory.getBean(_beanName);
    }

    /**
     * Get bean.
     * @param _beanName _beanName
     * @param _beanType _beanType
     * @return T
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     * @throws JCoBeanNotOfRequiredTypeSemaphore JCoBeanNotOfRequiredTypeSemaphore
     */
    public static <T> T getBean(String _beanName, Class<T> _beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        return _factory.getBean(_beanName, _beanType);
    }

    /**
     * Get bean.
     * @param _beanType _beanType
     * @return T
     * @throws NoUniqueJCoBeanSemaphore NoUniqueJCoBeanSemaphore
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     *
     */
    public static <T> T getBean(Class<T> _beanType) throws NoUniqueJCoBeanSemaphore, NoSuchJCoBeanSemaphore {
        return _factory.getBean(_beanType);
    }

    /**
     * Get beans.
     * @param _beanType _beanType
     * @return List<T>
     */
    public static <T> List<T> getBeans(Class<T> _beanType) {
        return _factory.getBeans(_beanType);
    }

    /**
     * Get beanName.
     * @param _beanType _beanType
     * @return String[]
     */
    public static String[] getBeanNamesForType(Class _beanType) {
        return _factory.getBeanNamesForType(_beanType);
    }

    /**
     * Un registry bean.
     * @param _beanName _beanName
     */
    public static void unRegistryBean(String _beanName) {
        _factory.unRegistry(_beanName);
    }
}
