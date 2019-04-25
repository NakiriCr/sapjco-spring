package org.yanzx.core.extend.sap.jco.server.handler.trait;

import com.sap.conn.jco.JCoFunctionTemplate;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 23:40
 */
public interface DynamicFunctionHandler {

    /**
     * Registry sap function.
     * @return JCoFunctionTemplate
     */
    JCoFunctionTemplate registrySapFunc();
}
