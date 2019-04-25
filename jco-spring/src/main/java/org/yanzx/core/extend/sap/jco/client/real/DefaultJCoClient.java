package org.yanzx.core.extend.sap.jco.client.real;

import com.sap.conn.jco.*;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncRequestHandler;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncResponseHandler;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.server.provider.CustomizeDestinationDataProvider;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/16 22:49
 */
public class DefaultJCoClient {

    private final JCoServerConfig _serverConfig;

    public DefaultJCoClient(JCoServerConfig _serverConfig) {
        this._serverConfig = _serverConfig;
    }

    /**
     * Destroy jcoClient.
     */
    public void destroy() {
        CustomizeDestinationDataProvider.getInstance().unRegistryDestination(_serverConfig.getDestinationName());
    }

    /**
     * Get destination.
     */
    public JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore {
        try {
            return JCoDestinationManager
                    .getDestination(_serverConfig.getDestinationName());
        }catch (JCoException _ex) { throw new JCoClientInvokeOnErrorSemaphore(_ex); }
    }

    /**
     * Get function.
     * @param _functionName _functionName
     */
    public JCoFunction getFunction(String _functionName) throws JCoClientInvokeOnErrorSemaphore {
        try {
            return JCoDestinationManager
                    .getDestination(_serverConfig.getDestinationName())
                    .getRepository()
                    .getFunction(_functionName);
        }catch (JCoException _ex) { throw new JCoClientInvokeOnErrorSemaphore(_ex); }
    }

    /**
     * Invoke sap function.
     * @param _requestHandler _requestHandler
     * @param _responseHandler _responseHandler
     * @throws JCoClientInvokeOnErrorSemaphore JCoClientInvokeOnErrorSemaphore
     */
    public void invokeSapFunc(String _functionName, SapFuncRequestHandler _requestHandler, SapFuncResponseHandler _responseHandler) throws JCoClientInvokeOnErrorSemaphore {
        try {
            /* Get function. */
            JCoFunction _func = getFunction(_functionName);

            if (_func == null)
                throw new JCoClientInvokeOnErrorSemaphore("Could not find function: [" + _functionName + "]");

            /* Request handle. */
            _requestHandler.handleRequest(_func.getImportParameterList(), _func.getTableParameterList(), _func.getChangingParameterList());

            /* Invoke. */
            JCoResponse _response = new DefaultRequest(_func).execute(getDestination());

            /* Response handle. */
            _responseHandler.handleResponse(_response);

        }catch (Exception _ex) { throw new JCoClientInvokeOnErrorSemaphore("Fail to invoke sap function: [" + _functionName + "]", _ex); }
    }

    /* ============================================================================================================= */

    static class DefaultRequest extends com.sap.conn.jco.rt.DefaultRequest {
        DefaultRequest(JCoFunction function) {
            super(function);
        }
    }

}
