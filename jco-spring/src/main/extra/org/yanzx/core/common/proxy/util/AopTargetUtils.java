package org.yanzx.core.common.proxy.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.yanzx.core.common.proxy.semaphore.AopTargetGetOnErrorSemaphore;

import java.lang.reflect.Field;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/9/3 20:21
 */
public class AopTargetUtils {

    private static final Log _logger = LogFactory.getLog(AopTargetUtils.class);

    /**
     * Get target object
     * @param _proxy proxy object
     */
    public static Object getTarget(Object _proxy) {
        try {
            if(!AopUtils.isAopProxy(_proxy)) {
                return _proxy; /* Not proxy object */
            }
            else {
                if(AopUtils.isJdkDynamicProxy(_proxy)) {
                    return getJdkDynamicProxyTargetObject(_proxy); /* JDK proxy */
                }
                else {
                    return getCglibProxyTargetObject(_proxy); /* Cglib proxy */
                }
            }
        }catch (Exception _ex){
            throw new AopTargetGetOnErrorSemaphore("Fail to get proxy target object.", _ex);
        }
    }

    /**
     * Get cglib proxy target object
     * @param _proxy proxy object
     */
    private static Object getCglibProxyTargetObject(Object _proxy) throws Exception {
        Field _field = _proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        _field.setAccessible(true);
        Object _dynamicAdvisedInterceptor = _field.get(_proxy);

        Field _advised = _dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        _advised.setAccessible(true);

        return ((AdvisedSupport)_advised.get(_dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    /**
     * Get JDK proxy target object
     * @param _proxy proxy object
     */
    private static Object getJdkDynamicProxyTargetObject(Object _proxy) throws Exception {
        Field _field = _proxy.getClass().getSuperclass().getDeclaredField("h");
        _field.setAccessible(true);
        AopProxy _aopProxy = (AopProxy) _field.get(_proxy);

        Field _advised = _aopProxy.getClass().getDeclaredField("advised");
        _advised.setAccessible(true);

        return ((AdvisedSupport)_advised.get(_aopProxy)).getTargetSource().getTarget();
    }

}
