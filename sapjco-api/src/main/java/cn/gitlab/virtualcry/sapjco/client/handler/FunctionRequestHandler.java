package cn.gitlab.virtualcry.sapjco.client.handler;

import com.sap.conn.jco.JCoParameterList;

/**
 * A handler before request sap function.
 *
 * @author VirtualCry
 */
public interface FunctionRequestHandler {

    /**
     * Handle before request.
     * @param importParameter One type of sap function input args.
     * @param tableParameter One type of sap function input args.
     * @param changingParameter One type of sap function input args.
     */
    void handle(JCoParameterList importParameter, JCoParameterList tableParameter, JCoParameterList changingParameter);
}
