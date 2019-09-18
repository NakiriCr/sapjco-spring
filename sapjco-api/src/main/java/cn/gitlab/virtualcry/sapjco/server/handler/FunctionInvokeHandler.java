package cn.gitlab.virtualcry.sapjco.server.handler;

import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

/**
 * A handler to listen SAP system invoking RFC function and handle it.
 *
 * @author VirtualCry
 */
public interface FunctionInvokeHandler extends JCoServerFunctionHandler {

    /**
     * Handle sap rfc function.
     * @param serverCtx serverCtx.
     * @param function rfc function.
     */
    void handleRequest(JCoServerContext serverCtx, JCoFunction function) throws AbapException, AbapClassException;
}
