package org.yanzx.core.extend.sap.jco.factory.support.util;

import org.yanzx.core.extend.sap.jco.common.util.JCoBeanHelper;
import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanCreationSemaphore;
import org.yanzx.core.extend.sap.jco.queue.JCoInitializeQueues;
import org.yanzx.core.extend.sap.jco.queue.task.JCoContextInitializeTask;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/31 1:09
 */
public class JCoBeanRegistryUtils {

    /**
     * Check singleton bean is registry or not.
     * @param _beanType _beanType
     * @return boolean
     */
    public static boolean isRegistryForSingletonBean(Class _beanType) {

        /* Registry flag. */
        boolean _isRegistry = false;

        /* Check in JCoBeanFactory */
        for (String _beanName : JCoBeanHelper.getBeanNamesForType(_beanType)) {
            if (_beanType.getName().equals(JCoBeanHelper.getBean(_beanName).getClass().getName())) {
                _isRegistry = true;
                break;
            }
        }

        return _isRegistry;
    }

    /* ============================================================================================================= */

    /**
     * Registry initialize tasks in JCoBeanFactory && Add to JCoInitializeQueues.
     * @param _bean _bean
     */
    public static void registryJCoInitializeTask(JCoContextInitializeTask _bean) throws JCoBeanCreationSemaphore {

        /* Register JCoInitializeTask in JCoBeanFactory. */
        registryJCoCommonBean(_bean);

        /* Add initialize task from JCoBeanFactory to JCoInitializeQueues. */
        JCoInitializeQueues.getSingleton().addTask(_bean);
    }

    /**
     * Registry initialize tasks in JCoBeanFactory && Add to JCoInitializeQueues.
     * @param _beanName _beanName
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public static void registryJCoInitializeTask(String _beanName, JCoContextInitializeTask _bean) throws JCoBeanCreationSemaphore {

        /* Register JCoInitializeTask in JCoBeanFactory. */
        registryJCoCommonBean(_beanName, _bean);

        /* Add initialize task from JCoBeanFactory to JCoInitializeQueues. */
        JCoInitializeQueues.getSingleton().addTask(_bean);
    }

    /* ============================================================================================================= */

    /**
     * Registry JCoCommonBean in JCoBeanFactory.
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public static void registryJCoCommonBean(Object _bean) throws JCoBeanCreationSemaphore {
        JCoBeanHelper.registry(_bean);
    }

    /**
     * Registry JCoCommonBean in JCoBeanFactory.
     * @param _beanName _beanName
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public static void registryJCoCommonBean(String _beanName, Object _bean) throws JCoBeanCreationSemaphore {
        JCoBeanHelper.registry(_beanName, _bean);
    }
}
