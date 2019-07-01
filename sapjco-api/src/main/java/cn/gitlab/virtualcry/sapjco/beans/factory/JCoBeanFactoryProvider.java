package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.beans.factory.semaphore.JCoBeanFactoryAlreadyRegisterSemaphore;

import java.util.function.Supplier;

/**
 * The Provider for bean factory.
 *
 * @author VirtualCry
 */
public class JCoBeanFactoryProvider {

    private JCoBeanFactory                              beanFactory;

    private static class JCoBeanFactoryProviderInstance {
        private static final JCoBeanFactoryProvider INSTANCE = new JCoBeanFactoryProvider();
    }
    private JCoBeanFactoryProvider() { }
    public static JCoBeanFactoryProvider getSingleton() {  // singleton
        return JCoBeanFactoryProviderInstance.INSTANCE;
    }


    /**
     * Get bean factory which is register in provider.
     * @return The registered {@link JCoBeanFactory}.
     */
    public JCoBeanFactory getIfAvailable() {
        return beanFactory;
    }


    /**
     * Get bean factory if it has been registered in provider.
     * Otherwise, use default value to register and get it.
     * @param defaultValue The {@link JCoBeanFactory} supplier to be used for registering.
     * @return The registered {@link JCoBeanFactory}.
     */
    public JCoBeanFactory getIfAvailable(Supplier<JCoBeanFactory> defaultValue) {
        if (!this.isAlreadyRegister()) {
            this.register(defaultValue.get());
        }
        return beanFactory;
    }


    /**
     * Register a bean factory implement in provider.
     * @param beanFactory  The {@link JCoBeanFactory} to be used for registering.
     */
    public void register(JCoBeanFactory beanFactory) {
        if (beanFactory == null)
            throw new IllegalArgumentException("Bean factory must not be null");
        if (this.isAlreadyRegister())
            throw new JCoBeanFactoryAlreadyRegisterSemaphore();
        // set
        this.beanFactory = beanFactory;
    }


    /**
     * Check provider if it exists registered bean factory.
     * @return {@literal check result}
     */
    public boolean isAlreadyRegister() {
        return beanFactory != null;
    }


    /**
     * Clear registered factory in provider.
     */
    public void clear() {
        beanFactory = null;
    }

}
