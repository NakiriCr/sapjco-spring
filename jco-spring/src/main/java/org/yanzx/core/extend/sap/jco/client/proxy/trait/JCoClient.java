package org.yanzx.core.extend.sap.jco.client.proxy.trait;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncRequestHandler;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncResponseHandler;
import org.yanzx.core.extend.sap.jco.client.real.DefaultJCoClient;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/17 0:23
 */
public interface JCoClient {

    /**
     * Init jcoClient.
     * @throws JCoClientStartOnErrorSemaphore JCoClientStartOnErrorSemaphore
     */
    void init() throws JCoClientStartOnErrorSemaphore;

    /**
     * Init jcoClient.
     * @param _factory _factory
     * @throws JCoClientStartOnErrorSemaphore JCoClientStartOnErrorSemaphore
     */
    void init(JCoBeanFactory _factory) throws JCoClientStartOnErrorSemaphore;

    /**
     * Destroy jcoClient.
     */
    void destroy();

    /* ============================================================================================================= */

    /**
     * Get real jcoServer.
     * @return com.sap.conn.jco.server.JCoServer
     */
    DefaultJCoClient getRealJCoClient();

    /* ============================================================================================================= */

    /**
     * Invoke sap function.
     * @param _requestHandler _requestHandler
     * @param _responseHandler _responseHandler
     * @throws JCoClientInvokeOnErrorSemaphore JCoClientInvokeOnErrorSemaphore
     */
    void invokeSapFunc(String _functionName, SapFuncRequestHandler _requestHandler, SapFuncResponseHandler _responseHandler) throws JCoClientInvokeOnErrorSemaphore;

    /**
     * Get destination.
     */
    JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore;

    /**
     * Get function.
     * @param _functionName _functionName
     */
    JCoFunction getFunction(String _functionName) throws JCoClientInvokeOnErrorSemaphore;
}
