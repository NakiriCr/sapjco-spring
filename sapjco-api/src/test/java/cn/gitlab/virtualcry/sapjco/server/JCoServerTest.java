package cn.gitlab.virtualcry.sapjco.server;

import cn.gitlab.virtualcry.sapjco.beans.factory.DefaultJCoBeanFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.DefaultJCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoBeanFactoryProvider;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactoryProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.handler.FunctionInvokeHandler;
import cn.gitlab.virtualcry.sapjco.util.data.JCoDataUtils;
import com.alibaba.fastjson.JSON;
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

    private JCoServer server;

    @Before
    public void initialize() {
        JCoBeanFactoryProvider.getSingleton().getIfAvailable()
                .register("GENERIC_HANDLER", (FunctionInvokeHandler) (serverCtx, function) -> {
                    System.out.println(JSON.toJSONString(
                            JCoDataUtils.getJCoParameterListValue(function.getImportParameterList())
                    ));
                    System.out.println(JSON.toJSONString(
                            JCoDataUtils.getJCoParameterListValue(function.getTableParameterList())
                    ));
                    System.out.println(JSON.toJSONString(
                            JCoDataUtils.getJCoParameterListValue(function.getExportParameterList())
                    ));
                    System.out.println(JSON.toJSONString(
                            JCoDataUtils.getJCoParameterListValue(function.getChangingParameterList())
                    ));
                    JCoServerTest.class.notify();
                });
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
        server = JCoConnectionFactoryProvider.getSingleton().getIfAvailable()
                .createServer("testServer", settings);
    }

    @Test
    public void testServer() {
        server.start();
        Semaphore semaphore = new Semaphore(0);
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) { ex.printStackTrace(); }
    }
}
