package cn.gitlab.virtualcry.jcospring.connect.server.handler;

import com.sap.conn.jco.JCoFunctionTemplate;

/**
 * Dynamic to register sap function
 *
 * @author VirtualCry
 */
public interface DynamicFunctionHandler {

    /**
     * register sap function.
     */
    JCoFunctionTemplate registerSapFunc();
}
