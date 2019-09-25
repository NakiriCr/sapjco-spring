package cn.gitlab.virtualcry.sapjco.client;

import cn.gitlab.virtualcry.sapjco.beans.factory.DefaultJCoBeanFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.DefaultJCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoBeanFactoryProvider;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactoryProvider;
import cn.gitlab.virtualcry.sapjco.client.function.zmm_shp_getdnhb.DnHeader;
import cn.gitlab.virtualcry.sapjco.client.function.zmm_shp_getdnhb.TableParameter;
import cn.gitlab.virtualcry.sapjco.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.util.data.JCoDataUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * Test for {@link JCoClient}.
 *
 * @author VirtualCry
 */
public class JCoClientTest {

    static {
        JCoDataProvider.registerInEnvironment();
        JCoBeanFactoryProvider.getSingleton()
                .register(new DefaultJCoBeanFactory());
        JCoConnectionFactoryProvider.getSingleton()
                .register(new DefaultJCoConnectionFactory());
    }

    private JCoClient client;

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
        client = JCoConnectionFactoryProvider.getSingleton().getIfAvailable()
                .createClient("testClient", settings);
    }

    @Test
    public void invokeSapFunc() {
        String functionName = "ZMM_SHP_GETDNHB";
        FunctionRequestHandler requestHandler = (importParameter, tableParameter, changingParameter) -> {
            TableParameter tableParameterValue = TableParameter.builder()
                    .dnHeaders(Collections.singletonList(DnHeader.builder().dnNo("0080055489").build()))
                    .build();
            JCoDataUtils.setJCoParameterListValue(tableParameter, tableParameterValue);
        };
        Map<String, Object> invokeResult = client.invokeSapFunc(functionName, requestHandler);
        TableParameter invokeResultForJavaBean = client.invokeSapFunc(functionName, requestHandler, TableParameter.class);
        System.out.println(JSON.toJSONString(invokeResult));
        System.out.println(JSON.toJSONString(invokeResultForJavaBean));
    }
}
