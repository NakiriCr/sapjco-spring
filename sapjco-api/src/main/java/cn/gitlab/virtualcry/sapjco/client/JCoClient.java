package cn.gitlab.virtualcry.sapjco.client;

import cn.gitlab.virtualcry.sapjco.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.sapjco.client.handler.FunctionResponseHandler;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;

/**
 *  Client using RFC protocol for communicating with SAP system, client could be used for
 *  viewing sap function definition, or invoking sap RFC function.
 *
 * @author VirtualCry
 */
public interface JCoClient extends AutoCloseable {

    /**
     * Release client connection.
     */
    void release();


    /**
     * Get client configuration.
     * @return The configuration {@link JCoSettings}
     */
    JCoSettings getSettings();


    /**
     * Get jco destination.
     * @return The destination {@link JCoDestination}
     */
    JCoDestination getDestination();


    /**
     * Get sap function.
     * @param functionName The {@literal functionName}
     *                     to be used for viewing sap function information.
     * @return The sap function {@link JCoFunction}
     */
    JCoFunction getFunction(String functionName);


    /**
     * Invoke sap function.
     * @param functionName The {@literal functionName}
     *                     to be used for matching sap function.
     * @param requestHandler The {@link FunctionRequestHandler}
     *                       to be used for doing somethings before request.
     * @param responseHandler he {@link FunctionResponseHandler}
     *                       to be used for handling function's response result.
     */
    void invokeSapFunc(String functionName,
                       FunctionRequestHandler requestHandler,
                       FunctionResponseHandler responseHandler);
}
