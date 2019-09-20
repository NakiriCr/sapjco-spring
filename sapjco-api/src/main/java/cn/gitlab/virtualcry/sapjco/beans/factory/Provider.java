package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.beans.factory.semaphore.JCoBeanFactoryAlreadyRegisterSemaphore;

import java.util.function.Supplier;

/**
 * Object instance provider.
 *
 * @author VirtualCry
 */
public class Provider<T> {

    private T                           instance;


    /**
     * Get provider instance.
     * @return object cache.
     */
    public T getIfAvailable() {
        return instance;
    }

    /**
     * Get instance in provider.
     * @param defaultValue The {@literal defaultValue} to be used for registry if it's absent.
     * @return instance in provider.
     */
    public synchronized T getIfAvailable(Supplier<T> defaultValue) {
        if (!this.isAlreadyRegister()) {
            this.register(defaultValue.get());
        }
        return instance;
    }

    /**
     * Register instance in provider.
     * @param instance The {@literal defaultValue} to be used for registry.
     */
    public synchronized void register(T instance) {
        if (instance == null)
            throw new IllegalArgumentException("Provider instance must not be null");
        if (this.isAlreadyRegister())
            throw new JCoBeanFactoryAlreadyRegisterSemaphore();
        // set
        this.instance = instance;
    }

    /**
     * Check for registration
     * @return check result.
     */
    public boolean isAlreadyRegister() {
        return instance != null;
    }

    /**
     * Clear instance in provider.
     */
    public void clear() {
        instance = null;
    }
}
