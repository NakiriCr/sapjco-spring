package org.yanzx.core.extend.sap.jco.client.handler.trait;

import com.sap.conn.jco.JCoParameterList;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/3/16 12:03
 */
public interface SapFuncRequestHandler {

    /**
     * Request handle.
     * @param _importParameter _importParameter
     * @param _tableParameter _tableParameter
     * @param _changingParameter _changingParameter
     */
    void handleRequest(JCoParameterList _importParameter, JCoParameterList _tableParameter, JCoParameterList _changingParameter);
}
