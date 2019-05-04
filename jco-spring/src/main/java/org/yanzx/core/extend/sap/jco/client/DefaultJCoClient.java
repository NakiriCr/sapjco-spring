package org.yanzx.core.extend.sap.jco.client;

import com.sap.conn.jco.*;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;
import org.yanzx.core.extend.sap.jco.client.handler.trait.FunctionRequestHandler;
import org.yanzx.core.extend.sap.jco.client.handler.trait.FunctionResponseHandler;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.client.trait.JCoClient;
import org.yanzx.core.extend.sap.jco.provider.JCoDataProvider;

/**
 * JCo client
 *
 * @author VirtualCry
 */
public class DefaultJCoClient implements JCoClient {

    private final JCoSettings settings;

    public DefaultJCoClient(JCoSettings settings) {
        // settings
        this.settings = settings;
        // init connection
        initJCoConnection(this.settings);
    }

    @Override
    public void release() {
        JCoDataProvider.getSingleton()
                .unRegisterClientSettings(settings.getSettingsName());
    }

    @Override
    public JCoSettings getSettings() {
        return settings;
    }

    @Override
    public JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore {
        try {
            return JCoDestinationManager
                    .getDestination(settings.getSettingsName());
        }catch (JCoException ex) { throw new JCoClientInvokeOnErrorSemaphore(ex); }
    }

    @Override
    public JCoFunction getFunction(String functionName) throws JCoClientInvokeOnErrorSemaphore {
        try {
            return JCoDestinationManager
                    .getDestination(settings.getSettingsName())
                    .getRepository()
                    .getFunction(functionName);
        }catch (JCoException ex) { throw new JCoClientInvokeOnErrorSemaphore(ex); }
    }

    @Override
    public void invokeSapFunc(String functionName,
                              FunctionRequestHandler requestHandler,
                              FunctionResponseHandler responseHandler) throws JCoClientInvokeOnErrorSemaphore {
        try {
            // get function
            JCoFunction function = getFunction(functionName);

            if (function == null)
                throw new JCoClientInvokeOnErrorSemaphore("Could not find function: [" + functionName + "]");

            // request handle
            requestHandler.handle(
                    function.getImportParameterList(),
                    function.getTableParameterList(),
                    function.getChangingParameterList()
            );

            // invoke
            JCoResponse response = new DefaultRequest(function).execute(getDestination());

            // response handle
            responseHandler.handle(response);

        }catch (JCoException ex) {
            throw new JCoClientInvokeOnErrorSemaphore("Fail to invoke sap function: [" + functionName + "]", ex); }
    }


    /* ============================================================================================================= */

    /**
     * init connection
     * @param settings settings
     */
    private static void initJCoConnection(JCoSettings settings) throws JCoClientCreatedOnErrorSemaphore {
        try {
            // register client
            JCoDataProvider.getSingleton()
                    .registerClientSettings(settings);

            // ping test
            JCoDestinationManager
                    .getDestination(settings.getSettingsName())
                    .ping();

        }catch (JCoException ex) { throw new JCoClientCreatedOnErrorSemaphore(
                "Unable to create the client: [" + settings.getSettingsName() + "]", ex); }
    }

    static class DefaultRequest extends com.sap.conn.jco.rt.DefaultRequest {
        DefaultRequest(JCoFunction function) {
            super(function);
        }
    }
}
