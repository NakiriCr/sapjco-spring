package cn.gitlab.virtualcry.sapjco.server.function.generic_handler;

import cn.gitlab.virtualcry.sapjco.server.JCoServerTest;
import cn.gitlab.virtualcry.sapjco.server.handler.FunctionInvokeHandler;
import cn.gitlab.virtualcry.sapjco.util.data.JCoDataUtils;
import com.alibaba.fastjson.JSON;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;

/**
 * Handler for {@literal GENERIC_NAME}
 *
 * @author VirtualCry
 */
public class GENERIC_FunctionInvokeHandler implements FunctionInvokeHandler {

    @Override
    public void handleRequest(JCoServerContext serverCtx, JCoFunction function) {
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
    }
}
