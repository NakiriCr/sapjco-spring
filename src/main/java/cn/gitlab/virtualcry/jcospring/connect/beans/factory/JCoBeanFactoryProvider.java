package cn.gitlab.virtualcry.jcospring.connect.beans.factory;

import cn.gitlab.virtualcry.jcospring.connect.beans.factory.semaphore.JCoBeanFactoryAlreadyRegisterSemaphore;

import java.util.function.Supplier;

/**
 * Bean factory provider
 *
 * @author VirtualCry
 */
public class JCoBeanFactoryProvider {

    // bean factory
    private static JCoBeanFactory beanFactory;

    public static JCoBeanFactory getIfAvailable() {
        return beanFactory;
    }

    public static JCoBeanFactory getIfAvailable(Supplier<JCoBeanFactory> defaultValue) {
        if (!isAlreadyRegister()) {
            register(defaultValue.get());
        }
        return beanFactory;
    }

    public static void register(JCoBeanFactory beanFactory) {
        if (beanFactory == null)
            throw new IllegalArgumentException("Bean factory must not be null");
        if (isAlreadyRegister())
            throw new JCoBeanFactoryAlreadyRegisterSemaphore();
        // set
        JCoBeanFactoryProvider.beanFactory = beanFactory;
    }

    public static boolean isAlreadyRegister() {
        return beanFactory != null;
    }

    public static void clear() {
        beanFactory = null;
    }
}
