package cn.gitlab.virtualcry.sapjco.beans.factory.semaphore;

import cn.gitlab.virtualcry.sapjco.beans.factory.JCoBeanFactoryProvider;

/**
 *  Factory already register semaphore
 *
 * @author VirtualCry
 */
public class JCoBeanFactoryAlreadyRegisterSemaphore extends RuntimeException {

    /**
     * Create a new {@code JCoBeanFactoryAlreadyRegisterSemaphore}.
     */
    public JCoBeanFactoryAlreadyRegisterSemaphore() {
        super("JCo bean factory: ["
                + JCoBeanFactoryProvider.getSingleton().getIfAvailable().getClass().getName()
                + "] has already register.");
    }
}
