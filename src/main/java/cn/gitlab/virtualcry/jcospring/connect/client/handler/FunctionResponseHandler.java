package cn.gitlab.virtualcry.jcospring.connect.client.handler;

import com.sap.conn.jco.JCoResponse;

/**
 * Response for sap function:
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
