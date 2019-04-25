package org.yanzx.core.extend.sap.jco.server.proxy;

import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.common.util.JCoBeanHelper;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;
import org.yanzx.core.extend.sap.jco.server.proxy.trait.AbstractJCoServerProxy;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 13:43
 */
public class JCoServerProxy extends AbstractJCoServerProxy {

    public JCoServerProxy(JCoServerConfig _jcoServerConfig) {
        super(_jcoServerConfig);
    }

    /**
     * Init and Start jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    public void initAndStart() throws JCoServerStartOnErrorSemaphore {
        initAndStart(JCoBeanHelper.getJCoBeanFactory());
    }

    /**
     * Init and Start jcoServer.
     * @param _factory _factory
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    public void initAndStart(JCoBeanFactory _factory) throws JCoServerStartOnErrorSemaphore {
        init(_factory);
        start();
    }
}
