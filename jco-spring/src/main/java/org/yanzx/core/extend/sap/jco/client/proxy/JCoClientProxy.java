package org.yanzx.core.extend.sap.jco.client.proxy;

import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.proxy.trait.AbstractJCoClientProxy;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.common.util.JCoBeanHelper;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/17 0:38
 */
public class JCoClientProxy extends AbstractJCoClientProxy {

    public JCoClientProxy(JCoServerConfig _serverConfig) {
        super(_serverConfig);
    }

    /**
     * Init jcoClient.
     * @throws JCoClientStartOnErrorSemaphore JCoClientStartOnErrorSemaphore
     */
    public void init() throws JCoClientStartOnErrorSemaphore {
        super.init(JCoBeanHelper.getJCoBeanFactory());
    }
}
