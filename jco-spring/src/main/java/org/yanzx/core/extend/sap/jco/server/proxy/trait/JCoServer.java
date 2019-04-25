package org.yanzx.core.extend.sap.jco.server.proxy.trait;

import com.sap.conn.jco.server.JCoServerState;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 23:57
 */
public interface JCoServer {

    /**
     * Init and Start jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    void initAndStart() throws JCoServerStartOnErrorSemaphore;

    /**
     * Init and Start jcoServer.
     * @param _factory _factory
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    void initAndStart(JCoBeanFactory _factory) throws JCoServerStartOnErrorSemaphore;

    /* ============================================================================================================= */

    /**
     * Start jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    void start() throws JCoServerStartOnErrorSemaphore;

    /**
     * Stop jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    void stop() throws JCoServerStartOnErrorSemaphore;

    /**
     * Get jcoServer state.
     * @return JCoServerState
     */
    JCoServerState getState();


    /* ============================================================================================================= */

    /**
     * Get real jcoServer.
     * @return com.sap.conn.jco.server.JCoServer
     */
    com.sap.conn.jco.server.JCoServer getRealJCoServer();
}
