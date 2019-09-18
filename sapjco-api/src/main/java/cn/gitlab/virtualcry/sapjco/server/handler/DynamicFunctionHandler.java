package cn.gitlab.virtualcry.sapjco.server.handler;

import com.sap.conn.jco.JCoFunctionTemplate;

/**
 * Define SAP RFC function.
 *
 * @author VirtualCry
 */
public interface DynamicFunctionHandler {

    /**
     * Register sap rfc function.
     * @return The definition of SAP RFC function.
     */
    JCoFunctionTemplate registerSapFunc();
}
