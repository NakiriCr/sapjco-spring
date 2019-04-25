package org.yanzx.core.extend.sap.jco.queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yanzx.core.extend.sap.jco.queue.task.JCoContextInitializeTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/29 16:07
 */
public class JCoInitializeQueues {

    /* Logout. */
    private static final Log _logger = LogFactory.getLog(JCoInitializeQueues.class);

    /* ============================================================================================================= */

    private JCoInitializeQueues() { }
    private static class JCoInitializeQueuesInstance {
        private static final JCoInitializeQueues INSTANCE = new JCoInitializeQueues();
    }
    public static JCoInitializeQueues getSingleton() {
        return JCoInitializeQueuesInstance.INSTANCE; /* Singleton */
    }


    /* ============================================================================================================= */

    /* Queues */
    private final Map<String, JCoContextInitializeTask> _queues = new ConcurrentHashMap<>();

    /**
     * Add task.
     * @param _task _task
     */
    public void addTask(JCoContextInitializeTask _task) {
        if (!_queues.keySet().contains(_task.getName())) {
            _queues.put(_task.getName(), _task);
            _logger.info("==> Adding task: [" + _task.getName() + "] to JCoInitializeQueues.");
        }
    }

    /**
     * Cover task if it exists.
     * @param _task _task
     */
    public void coverTask(JCoContextInitializeTask _task) {
        if (_queues.keySet().contains(_task.getName())) {
            _queues.put(_task.getName(), _task);
            _logger.info("==> Covering task: [" + _task.getName() + "] to JCoInitializeQueues.");
        }
    }


    /* ============================================================================================================= */

    /**
     * Get task.
     * @param _taskName _taskName
     * @return JCoContextInitializeTask
     */
    public JCoContextInitializeTask getTask(String _taskName) {
        return _queues.get(_taskName);
    }

    /**
     * Get tasks.
     * @return Collection<JCoContextInitializeTask>
     */
    public Collection<JCoContextInitializeTask> getTasks() {
        return _queues.values();
    }


    /* ============================================================================================================= */

    /**
     * Get ordered tasks.
     * @param  _order _order
     * @return Collection<JCoContextInitializeTask>
     */
    public Collection<JCoContextInitializeTask> getOrderedTasks(Comparator<JCoContextInitializeTask> _order) {
        /* Get initialize task. */
        List<JCoContextInitializeTask> _taskQueues = new ArrayList<>(_queues.values());
        /* Task Sort. */
        _taskQueues.sort(_order);
        /* Return order list. */
        return _taskQueues;
    }

    public static class Ordered {

        /**
         * 根据 Order 排序 - 【升序】
         */
        public final  static Comparator<JCoContextInitializeTask> ASC = new Comparator<JCoContextInitializeTask>() {
            @Override
            public int compare(JCoContextInitializeTask _after, JCoContextInitializeTask _before) {
                return Integer.compare(_after.getOrder(), _before.getOrder());
            }
        };

        /**
         * 根据 Order 排序 - 【降序】
         */
        public final static Comparator<JCoContextInitializeTask> DESC = new Comparator<JCoContextInitializeTask>() {
            @Override
            public int compare(JCoContextInitializeTask _after, JCoContextInitializeTask _before) {
                return Integer.compare(_before.getOrder(), _after.getOrder());
            }
        };
    }
}
