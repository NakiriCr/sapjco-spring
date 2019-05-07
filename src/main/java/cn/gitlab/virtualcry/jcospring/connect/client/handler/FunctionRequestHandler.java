package cn.gitlab.virtualcry.jcospring.connect.client.handler;

import com.sap.conn.jco.JCoParameterList;

/**
 * Request for sap function
 *
 * @author VirtualCry
 */
public interface FunctionRequestHandler {

    /**
     * request handle
     * @param importParameter importParameter
     * @param tableParameter tableParameter
     * @param changingParameter changingParameter
     */
    void handle(JCoParameterList importParameter, JCoParameterList tableParameter, JCoParameterList changingParameter);
}