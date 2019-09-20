package cn.gitlab.virtualcry.sapjco.beans.factory;

/**
 * Provider for {@link JCoConnectionFactory}.
 *
 * @author VirtualCry
 */
public class JCoConnectionFactoryProvider extends Provider<JCoConnectionFactory> {

    private static class JCoBeanFactoryProviderInstance {
        private static final JCoConnectionFactoryProvider INSTANCE = new JCoConnectionFactoryProvider();
    }

    private JCoConnectionFactoryProvider() { }

    public static JCoConnectionFactoryProvider getSingleton() {  // singleton
        return JCoBeanFactoryProviderInstance.INSTANCE;
    }
}
