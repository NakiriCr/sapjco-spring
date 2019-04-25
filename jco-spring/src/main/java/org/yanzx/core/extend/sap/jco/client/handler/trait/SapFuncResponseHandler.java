package org.yanzx.core.extend.sap.jco.client.handler.trait;

import com.sap.conn.jco.JCoResponse;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/3/16 12:11
 */
public interface SapFuncResponseHandler {

    /**
     * Response handle.
     * @param _response _response
     */
    void handleResponse(JCoResponse _response);
}
