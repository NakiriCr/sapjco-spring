package cn.gitlab.virtualcry.sapjco.client;

import cn.gitlab.virtualcry.sapjco.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.sapjco.client.handler.FunctionResponseHandler;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.config.Connections;
import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import com.sap.conn.jco.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implement of {@link JCoClient}
 *
 * @author VirtualCry
 */
@Slf4j
public class DefaultJCoClient implements JCoClient {

    private final JCoSettings                           settings;

    public DefaultJCoClient(JCoSettings settings) {
        this.settings = settings;
        initJCoConnection(this.settings);
    }


    @Override
    public void release() {
        JCoDataProvider.getSingleton()
                .unRegisterClientSettings(this.settings.getUniqueKey(Connections.CLIENT));

        if (log.isDebugEnabled())
            log.debug("JCoClient: [" + this.settings.getUniqueKey(Connections.CLIENT) + "] released.");
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
                    .getDestination(this.settings.getUniqueKey(Connections.CLIENT));
        }catch (JCoException ex) { throw new JCoClientInvokeOnErrorSemaphore(ex); }
    }

    @Override
    public JCoFunction getFunction(String functionName) {
        try {
            return JCoDestinationManager
                    .getDestination(this.settings.getUniqueKey(Connections.CLIENT))
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


    /* ============================================================================================================= */

    /**
     * Init connection
     * @param settings The {@literal settings} to be used for initializing connection.
     */
    private static void initJCoConnection(JCoSettings settings) {
        try {
            // register client
            JCoDataProvider.getSingleton()
                    .registerClientSettings(settings);

            // ping test
            JCoDestinationManager
                    .getDestination(settings.getUniqueKey(Connections.CLIENT))
                    .ping();

        }catch (JCoException ex) { throw new JCoClientCreatedOnErrorSemaphore(
                "Unable to create the client: [" + settings.getUniqueKey(Connections.CLIENT) + "]", ex); }
    }

    static class DefaultRequest extends com.sap.conn.jco.rt.DefaultRequest {
        DefaultRequest(JCoFunction function) {
            super(function);
        }
    }

}
