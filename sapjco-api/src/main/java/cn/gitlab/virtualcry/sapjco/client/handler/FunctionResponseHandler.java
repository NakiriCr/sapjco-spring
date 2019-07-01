package cn.gitlab.virtualcry.sapjco.client.handler;

import com.sap.conn.jco.JCoResponse;

/**
 *  A handler to handle response result.
 *
 * @author VirtualCry
 */
public interface FunctionResponseHandler {

    /**
     * Handle response result
     * @param response response
     */
    void handle(JCoResponse response);
}
