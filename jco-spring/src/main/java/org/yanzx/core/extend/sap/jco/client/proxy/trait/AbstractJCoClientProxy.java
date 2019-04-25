package org.yanzx.core.extend.sap.jco.client.proxy.trait;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.yanzx.core.common.aggregate.factory.IdentifierGenerator;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncRequestHandler;
import org.yanzx.core.extend.sap.jco.client.handler.trait.SapFuncResponseHandler;
import org.yanzx.core.extend.sap.jco.client.real.DefaultJCoClient;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;
import org.yanzx.core.extend.sap.jco.server.provider.CustomizeDestinationDataProvider;
import org.yanzx.core.extend.sap.jco.server.proxy.trait.JCoServer;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.yanzx.core.extend.sap.jco.common.util.JCoBeanHelper.unRegistryBean;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/17 0:24
 */
public abstract class AbstractJCoClientProxy implements JCoClient {

    /* Log. */
    protected static final Log _logger = LogFactory.getLog(JCoServer.class);

    /* Real JCo Client. */
    protected DefaultJCoClient _jcoClient;

    /* _serverContext. */
    protected final JCoServerConfig _serverConfig;

    protected AbstractJCoClientProxy(JCoServerConfig _serverConfig) {
        if (_serverConfig == null)
            throw new JCoClientStartOnErrorSemaphore("Could not find jco server config.");
        this._serverConfig = _serverConfig;

        /* Distribute an unique destination if it is empty. */
        if (StringUtils.isEmpty(this._serverConfig.getDestinationName())) {
            this._serverConfig.setDestinationName(String.valueOf(IdentifierGenerator.build()));
        }
    }

    /* ============================================================================================================= */

    /* Thread safe init flag. */
    protected volatile AtomicBoolean _isInit = new AtomicBoolean(false);

    /**
     * Init jcoClient.
     * @param _factory _factory
     * @throws JCoClientStartOnErrorSemaphore JCoClientStartOnErrorSemaphore
     */
    public void init(JCoBeanFactory _factory) throws JCoClientStartOnErrorSemaphore {

        if (!_isInit.compareAndSet(false, true))  {
            _logger.info("JCoClient: [" + _serverConfig.getDestinationName() + "] has been initialized.");
            return;
        }

        if (_factory == null)
            throw new JCoClientStartOnErrorSemaphore("Could not find jco factory.");

        /* Init. */
        initJco(_serverConfig);

        /* Get real jcoClient instance. */
        _jcoClient = new DefaultJCoClient(_serverConfig);
        /* Ping. */
        try {
            _jcoClient.getDestination().ping();
        }catch (JCoException _ex) { throw new JCoClientStartOnErrorSemaphore(_ex); }

        /* Registry jCoClient. */
        _factory.registry(_serverConfig.getDestinationName(), this);

        if (_logger.isDebugEnabled())
            _logger.debug("JCoClient: [" + _serverConfig.getDestinationName() + "] init.");
    }

    /**
     * Destroy jcoClient.
     */
    public void destroy() {
        if (_logger.isDebugEnabled())
            _logger.debug("JCoClient: [" + _serverConfig.getDestinationName() + "] destroyed.");

        _jcoClient.destroy();
        unRegistryBean( _serverConfig.getDestinationName());
        _isInit.set(false);
    }

    /* ============================================================================================================= */

    /**
     * Get real jcoServer.
     * @return com.sap.conn.jco.server.JCoServer
     */
    public DefaultJCoClient getRealJCoClient() {
        return _jcoClient;
    }

    /* ============================================================================================================= */

    /**
     * Invoke sap function.
     * @param _requestHandler _requestHandler
     * @param _responseHandler _responseHandler
     * @throws JCoClientInvokeOnErrorSemaphore JCoClientInvokeOnErrorSemaphore
     */
    public void invokeSapFunc(String _functionName, SapFuncRequestHandler _requestHandler, SapFuncResponseHandler _responseHandler) throws JCoClientInvokeOnErrorSemaphore {
        if (!_isInit.get())
            throw new JCoClientInvokeOnErrorSemaphore("JCoClient [" + _serverConfig.getDestinationName() + "] hasn't been initialized yet.");

        _jcoClient.invokeSapFunc(_functionName, _requestHandler, _responseHandler);
    }

    /**
     * Get destination.
     */
    public JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore {
        return _jcoClient.getDestination();
    }

    /**
     * Get function.
     * @param _functionName _functionName
     */
    public JCoFunction getFunction(String _functionName) throws JCoClientInvokeOnErrorSemaphore {
        return _jcoClient.getFunction(_functionName);
    }

    /* ============================================================================================================= */

    /**
     * Init jco.
     * @param _config sapConfig.
     */
    private static void initJco(JCoServerConfig _config) {

        /* Init connectionPool */
        initConnectionPool(_config);
    }

    /**
     * Init connectionPool.
     * @param _config sapConfig.
     */
    private static void initConnectionPool(JCoServerConfig _config) throws JCoServerStartOnErrorSemaphore {

        /* Add _destination config. */
        CustomizeDestinationDataProvider.getInstance().registryDestination(_config);
    }
}