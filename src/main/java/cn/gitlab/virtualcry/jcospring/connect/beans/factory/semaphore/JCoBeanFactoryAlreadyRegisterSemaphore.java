package cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore;

import cn.gitlab.virtualcry.jcospring.connect.beans.factory.JCoBeanFactoryProvider;

/**
 *  Factory already register semaphore
 *
 * @author VirtualCry
 */
public class JCoBeanFactoryAlreadyRegisterSemaphore extends RuntimeException {

    /** Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public JCoBeanFactoryAlreadyRegisterSemaphore() {
        super("JCo bean factory: [" + JCoBeanFactoryProvider.getIfAvailable().getClass().getName() + "] has already register.");
    }
}
