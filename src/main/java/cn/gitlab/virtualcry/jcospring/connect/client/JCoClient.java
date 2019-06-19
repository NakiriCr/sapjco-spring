package cn.gitlab.virtualcry.jcospring.connect.client;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoSettings;
import cn.gitlab.virtualcry.jcospring.connect.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.jcospring.connect.client.handler.FunctionResponseHandler;
import cn.gitlab.virtualcry.jcospring.connect.client.semaphore.JCoClientInvokeOnErrorSemaphore;

/**
 *  JCo Client
 *
 * @author VirtualCry
 */
public interface JCoClient extends AutoCloseable {

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
