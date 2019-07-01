package cn.gitlab.virtualcry.sapjco.beans.factory.semaphore;

/**
 * Bean already register semaphore
 *
 * @author VirtualCry
 */
public class JCoBeanAlreadyRegisterSemaphore extends RuntimeException {

    /**
     * Create a new {@code JCoBeanAlreadyRegisterSemaphore}.
     * @param beanName the registered bean name.
     */
    public JCoBeanAlreadyRegisterSemaphore(String beanName) {
        super("JCoBean: [" + beanName + "] has already register.");
    }
}
