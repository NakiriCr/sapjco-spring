package cn.gitlab.virtualcry.sapjco.server;

import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.Semaphore;

/**
 * Test for {@link JCoServer}.
 *
 * @author VirtualCry
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JCoServerTest {

    @Resource
    private JCoConnectionFactory connectionFactory;

    @Before
    public void initialize() {
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
