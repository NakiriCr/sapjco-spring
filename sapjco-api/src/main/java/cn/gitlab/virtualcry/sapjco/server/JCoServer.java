package cn.gitlab.virtualcry.sapjco.server;

import com.sap.conn.jco.server.JCoServerState;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;

/**
 * Server using RFC protocol for communicating with SAP system, server could be used for
 * listening SAP RFC function invoking and handle it.
 *
 * @author VirtualCry
 */
public interface JCoServer {

    /**
     * Start to connect SAP system with RFC protocol.
     */
    void start();


    /**
     * Disconnect from SAP system.
     */
    void stop();


    /**
     * Release RFC connections and destroy it.
     */
    void release();


    /**
     * Get server configuration.
     * @return The configuration {@link JCoSettings}
     */
    JCoSettings getSettings();


    /**
     * Get original server.
     * @return The original server {@link com.sap.conn.jco.server.JCoServer}
     */
    com.sap.conn.jco.server.JCoServer getOriginalServer();


    /**
     * Get server state.
     * @return The server state {@link JCoServerState}.
     */
    JCoServerState getState();

}
