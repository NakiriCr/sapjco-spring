package org.yanzx.core.extend.sap.jco.client.handler.trait;

import com.sap.conn.jco.JCoResponse;

/**
 * Response for sap callback function:
 *
 * @author VirtualCry
 */
public interface FunctionResponseHandler {

    /**
     * response handle.
     * @param response response
     */
    void handle(JCoResponse response);
}
