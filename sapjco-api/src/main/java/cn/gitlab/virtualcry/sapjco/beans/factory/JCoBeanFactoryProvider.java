package cn.gitlab.virtualcry.sapjco.beans.factory;

/**
 * Provider for {@link JCoBeanFactory}.
 *
 * @author VirtualCry
 */
public class JCoBeanFactoryProvider extends Provider<JCoBeanFactory> {

    private static class JCoBeanFactoryProviderInstance {
        private static final JCoBeanFactoryProvider INSTANCE = new JCoBeanFactoryProvider();
    }

    private JCoBeanFactoryProvider() { }

    public static JCoBeanFactoryProvider getSingleton() {  // singleton
        return JCoBeanFactoryProviderInstance.INSTANCE;
    }
}
