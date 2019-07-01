package cn.gitlab.virtualcry.sapjco.beans.factory.semaphore;

/**
 * Bean type error semaphore
 *
 * @author VirtualCry
 */
public class JCoBeanNotOfRequiredTypeSemaphore extends RuntimeException {

    private String                          beanName;
    private Class<?>                        requiredType;
    private Class<?>                        actualType;


    /**
     * Create a new JCoBeanNotOfRequiredTypeSemaphore.
     * @param beanName the name of the bean requested
     * @param requiredType the required type
     * @param actualType the actual type returned, which did not match
     * the expected type
     */
    public JCoBeanNotOfRequiredTypeSemaphore(String beanName, Class<?> requiredType, Class<?> actualType) {
        super("Bean named '" + beanName + "' is expected to be of type [" + requiredType.getName() +
                "] but was actually of type [" + actualType.getName() + "]");
        this.beanName = beanName;
        this.requiredType = requiredType;
        this.actualType = actualType;
    }


    /**
     * Return the name of the instance that was of the wrong type.
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Return the expected type for the bean.
     */
    public Class<?> getRequiredType() {
        return this.requiredType;
    }

    /**
     * Return the actual type of the instance found.
     */
    public Class<?> getActualType() {
        return this.actualType;
    }
}
