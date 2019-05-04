package org.yanzx.core.extend.sap.jco.server.trait;

import com.sap.conn.jco.server.JCoServerState;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;

/**
 * JCo Server
 *
 * @author VirtualCry
 */
public interface JCoServer {


    /**
     * start server
     */
    void start();


    /**
     * stop server
     */
    void stop();


    /**
     * release server
     */
    void release();


    /**
     * get settings
     */
    JCoSettings getSettings();


    /**
     * get real server
     */
    com.sap.conn.jco.server.JCoServer getRealServer();


    /**
     * get server state
     */
    JCoServerState getState();
}
