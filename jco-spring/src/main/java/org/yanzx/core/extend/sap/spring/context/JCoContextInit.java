package org.yanzx.core.extend.sap.spring.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.yanzx.core.common.proxy.util.AopTargetUtils;
import org.yanzx.core.extend.sap.jco.queue.JCoInitializeQueues;
import org.yanzx.core.extend.sap.jco.queue.task.JCoContextInitializeTask;
import org.yanzx.core.extend.sap.spring.beans.JCoBeanNameCache;
import org.yanzx.core.extend.sap.spring.stereotype.JCoContextListener;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.yanzx.core.extend.sap.jco.factory.support.util.JCoBeanRegistryUtils.registryJCoCommonBean;
import static org.yanzx.core.extend.sap.jco.factory.support.util.JCoBeanRegistryUtils.registryJCoInitializeTask;

/**
 * Description: JCoContext Init.
 *
 * @author VirtualCry
 * @date 2018/12/27 12:57
 */
@JCoContextListener
public class JCoContextInit implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    /* Logout. */
    private static final Log _logger = LogFactory.getLog(JCoContextInit.class);

    /* Thread safe init flag. */
    private volatile AtomicBoolean _isInit = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent _event) {

        if (!_isInit.compareAndSet(false, true))  return;

        /* Init JcoBeanFactory. */
        initJCoBeanFactory(_event.getApplicationContext());

        /* Run initialize tasks. */
        for (JCoContextInitializeTask _initializeTask : JCoInitializeQueues.getSingleton().getOrderedTasks(JCoInitializeQueues.Ordered.ASC)) {

            _logger.info("==> Running task: [" + _initializeTask.getName() + "].");

            /* Task run. */
            _initializeTask.run();
        }
    }

    private static void initJCoBeanFactory(ApplicationContext _context) {
        for (String _beanName : JCoBeanNameCache.getCaches()) {

            /* Get real _bean */
            Object _bean = AopTargetUtils.getTarget(_context.getBean(_beanName));

            /* Registry initialize tasks in JCoBeanFactory && Add to JCoInitializeQueues. */
            if (_bean instanceof JCoContextInitializeTask) {
                registryJCoInitializeTask(_beanName, (JCoContextInitializeTask) _bean);
                continue;
            }

            /* Registry JCoCommonBean in JCoBeanFactory. */
            registryJCoCommonBean(_beanName, _bean);
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
