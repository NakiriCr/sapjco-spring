package cn.gitlab.virtualcry.sapjco.client;

import cn.gitlab.virtualcry.sapjco.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.sapjco.client.handler.FunctionResponseHandler;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.util.data.JCoDataUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.sap.conn.jco.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static cn.gitlab.virtualcry.sapjco.config.Connections.CLIENT;

/**
 * Default implement of {@link JCoClient}
 *
 * @author VirtualCry
 */
@Slf4j
public class DefaultJCoClient implements JCoClient {

    private final JCoSettings               settings;

    public DefaultJCoClient(JCoSettings settings) {
        this.settings = settings;
        initJCoConnection(this.settings);
    }


    @Override
    public void release() {
        JCoDataProvider.getSingleton()
                .unRegisterClientSettings(this.settings.getUniqueKey(CLIENT));

        if (log.isDebugEnabled())
            log.debug("JCoClient: [" + this.settings.getUniqueKey(CLIENT) + "] released.");
    }

    @Override
    public void close() {
        this.release();
    }

    @Override
    public JCoSettings getSettings() {
        return this.settings;
    }

    @Override
    public JCoDestination getDestination() {
        try {
            return JCoDestinationManager
                    .getDestination(this.settings.getUniqueKey(CLIENT));
        }catch (JCoException ex) { throw new JCoClientInvokeOnErrorSemaphore(ex); }
    }

    @Override
    public JCoFunction getFunction(String functionName) {
        try {
            return JCoDestinationManager
                    .getDestination(this.settings.getUniqueKey(CLIENT))
                    .getRepository()
                    .getFunction(functionName);
        }catch (JCoException ex) { throw new JCoClientInvokeOnErrorSemaphore(ex); }
    }

    @Override
    public void invokeSapFunc(String functionName,
                              FunctionRequestHandler requestHandler,
                              FunctionResponseHandler responseHandler) {
        try {
            // get function
            JCoFunction function = this.getFunction(functionName);

            if (function == null)
                throw new JCoClientInvokeOnErrorSemaphore("Could not find function: [" + functionName + "]");

            // request handle
            requestHandler.handle(
                    function.getImportParameterList(),
                    function.getTableParameterList(),
                    function.getChangingParameterList()
            );

            // invoke
            JCoResponse response = new DefaultRequest(function).execute(this.getDestination());

            // response handle
            responseHandler.handle(response);

        }catch (JCoException ex) {
            throw new JCoClientInvokeOnErrorSemaphore("Fail to invoke sap function: [" + functionName + "]", ex); }
    }

    @Override
    public Map<String, Object> invokeSapFunc(String functionName, FunctionRequestHandler requestHandler) {
        Map<String, Object> invokeResult = new HashMap<>();
        FunctionResponseHandler responseHandler = response -> response
                .forEach(jCoField ->
                        invokeResult.put(jCoField.getName(), JCoDataUtils.getJCoFieldValue(jCoField))
                );
        this.invokeSapFunc(functionName, requestHandler, responseHandler);
        return invokeResult;
    }

    @Override
    public Map<String, Object> invokeSapFunc(String functionName,
                                             Object importParameterValue,
                                             Object tablesParameterValue, Object changingParameterValue) {
        FunctionRequestHandler requestHandler = setParameterRequestHandlerFunc.apply(InvokeParameter.builder()
                .importParameterValue(importParameterValue)
                .tablesParameterValue(tablesParameterValue)
                .changingParameterValue(changingParameterValue)
                .build());
        return this.invokeSapFunc(functionName, requestHandler);
    }

    @Override
    public <T> T invokeSapFunc(String functionName, FunctionRequestHandler requestHandler, Class<T> resultType) {
        Map<String, Object> invokeResult = this.invokeSapFunc(functionName, requestHandler);
        return TypeUtils.castToJavaBean(invokeResult, resultType);
    }

    @Override
    public <T> T invokeSapFunc(String functionName,
                               Object importParameterValue,
                               Object tablesParameterValue,
                               Object changingParameterValue, Class<T> resultType) {
        FunctionRequestHandler requestHandler = setParameterRequestHandlerFunc.apply(InvokeParameter.builder()
                .importParameterValue(importParameterValue)
                .tablesParameterValue(tablesParameterValue)
                .changingParameterValue(changingParameterValue)
                .build());
        return this.invokeSapFunc(functionName, requestHandler, resultType);
    }

    private Function<InvokeParameter, FunctionRequestHandler> setParameterRequestHandlerFunc = invokeParameter ->
            (importParameter, tableParameter, changingParameter) -> {
        if (invokeParameter.getImportParameterValue() != null)
            JCoDataUtils.setJCoParameterListValue(importParameter, invokeParameter.getImportParameterValue());
        if (invokeParameter.getTablesParameterValue() != null)
            JCoDataUtils.setJCoParameterListValue(tableParameter, invokeParameter.getTablesParameterValue());
        if (invokeParameter.getChangingParameterValue() != null)
            JCoDataUtils.setJCoParameterListValue(changingParameter, invokeParameter.getChangingParameterValue());
    };

    @Builder @Getter
    private static class InvokeParameter {
        private Object importParameterValue;
        private Object tablesParameterValue;
        private Object changingParameterValue;
    }

    /* ============================================================================================================= */

    /**
     * Init connection
     * @param settings The {@literal settings} to be used for initializing connection.
     */
    private static void initJCoConnection(JCoSettings settings) {
        try {
            // register client properties.
            JCoDataProvider.getSingleton()
                    .registerClientSettings(settings);

            // ping test
            JCoDestinationManager
                    .getDestination(settings.getUniqueKey(CLIENT))
                    .ping();

        }catch (JCoException ex) {
            // unregister client properties.
            JCoDataProvider.getSingleton()
                    .unRegisterClientSettings(settings.getUniqueKey(CLIENT));
            throw new JCoClientCreatedOnErrorSemaphore(
                    "Unable to create the client: [" + settings.getUniqueKey(CLIENT) + "]", ex);
        }
    }

    static class DefaultRequest extends com.sap.conn.jco.rt.DefaultRequest {
        DefaultRequest(JCoFunction function) {
            super(function);
        }
    }
}
