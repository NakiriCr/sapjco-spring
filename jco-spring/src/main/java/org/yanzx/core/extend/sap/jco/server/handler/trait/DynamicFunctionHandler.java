package org.yanzx.core.extend.sap.jco.server.handler.trait;

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
