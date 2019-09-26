package cn.gitlab.virtualcry.sapjco.client;

import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.client.function.zmm_shp_getdnhb.DnHeader;
import cn.gitlab.virtualcry.sapjco.client.function.zmm_shp_getdnhb.TableParameter;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * Test for {@link JCoClient}.
 *
 * @author VirtualCry
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JCoClientTest {

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
                .build();
        connectionFactory.createClient("testClient", settings);
    }

    @Test
    public void invokeSapFunc() {
        JCoClient client = connectionFactory.getClient("testClient");
        String functionName = "ZMM_SHP_GETDNHB";
        TableParameter tableParameterValue = TableParameter.builder()
                .dnHeaders(Collections.singletonList(DnHeader.builder().dnNo("0080055489").build()))
                .build();
        Map<String, Object> invokeResult = client.invokeSapFunc(functionName, null, tableParameterValue, null);
        TableParameter invokeResultForJavaBean = client.invokeSapFunc(functionName, null, tableParameterValue, null, TableParameter.class);
        System.out.println(JSON.toJSONString(invokeResult));
        System.out.println(JSON.toJSONString(invokeResultForJavaBean));
    }
}
