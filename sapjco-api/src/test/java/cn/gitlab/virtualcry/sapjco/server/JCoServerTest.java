package cn.gitlab.virtualcry.sapjco.server;

import cn.gitlab.virtualcry.sapjco.beans.factory.*;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.function.generic_handler.GENERIC_FunctionInvokeHandler;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoErrorListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoExceptionListener;
import cn.gitlab.virtualcry.sapjco.server.listener.DefaultJCoStateChangedListener;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * Test for {@link JCoServer}.
 *
 * @author VirtualCry
 */
public class JCoServerTest {

    static {
        JCoDataProvider.registerInEnvironment();
        JCoBeanFactoryProvider.getSingleton()
                .register(new DefaultJCoBeanFactory());
        JCoConnectionFactoryProvider.getSingleton()
                .register(new DefaultJCoConnectionFactory());
    }

    private final JCoBeanFactory beanFactory;
    private final JCoConnectionFactory connectionFactory;

    public JCoServerTest() {
        this.beanFactory = JCoBeanFactoryProvider.getSingleton().getIfAvailable();
        this.connectionFactory = JCoConnectionFactoryProvider.getSingleton().getIfAvailable();
    }

    @Before
    public void initialize() {
        beanFactory.register("GENERIC_HANDLER", new GENERIC_FunctionInvokeHandler());
        beanFactory.register(DefaultJCoExceptionListener.class.getName(), new DefaultJCoExceptionListener());
        beanFactory.register(DefaultJCoErrorListener.class.getName(), new DefaultJCoErrorListener());
        beanFactory.register(DefaultJCoStateChangedListener.class.getName(), new DefaultJCoStateChangedListener());
        JCoSettings settings = JCoSettings.builder()
                .ashost("192.168.0.51")
                .sysnr("00")
                .client("200")
                .user("ittest")
                .password("987654321w")
                .language("ZH")
                .poolCapacity("20")
                .peakLimit("10")
                .gatewayHost("192.168.0.51")
                .gatewayService("sapgw00")
                .programId("JAVA_JCO_SRM")
                .build();
        connectionFactory.createServer("testServer", settings);
    }

    @Test
    public void testServer() {
        connectionFactory.getServer("testServer").start();
        Semaphore semaphore = new Semaphore(0);
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) { ex.printStackTrace(); }
    }
}
