package org.yanzx.core.extend.sap.jco.client.trait;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;
import org.yanzx.core.extend.sap.jco.client.handler.trait.FunctionRequestHandler;
import org.yanzx.core.extend.sap.jco.client.handler.trait.FunctionResponseHandler;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;

/**
 *  JCo Client
 *
 * @author VirtualCry
 */
public interface JCoClient {

    /**
     * release client
     */
    void release();


    /**
     * get settings
     */
    JCoSettings getSettings();


    /**
     * get sap destination
     */
    JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore;


    /**
     * get sap function
     * @param functionName function name
     */
    JCoFunction getFunction(String functionName) throws JCoClientInvokeOnErrorSemaphore;


    /**
     * invoke sap function
     * @param functionName function name
     * @param requestHandler request handle
     * @param responseHandler response handle
     */
    void invokeSapFunc(String functionName,
                       FunctionRequestHandler requestHandler,
                       FunctionResponseHandler responseHandler) throws JCoClientInvokeOnErrorSemaphore;
}
