package org.yanzx.core.extend.sap.jco.server.proxy.trait;

import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoCustomRepository;
import com.sap.conn.jco.server.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.yanzx.core.common.aggregate.factory.IdentifierGenerator;
import org.yanzx.core.common.proxy.util.AopTargetUtils;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.factory.support.JCoBeanFactory;
import org.yanzx.core.extend.sap.jco.server.handler.trait.DynamicFunctionHandler;
import org.yanzx.core.extend.sap.jco.server.provider.CustomizeDestinationDataProvider;
import org.yanzx.core.extend.sap.jco.server.provider.CustomizeServerDataProvider;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;
import org.yanzx.core.extend.sap.jco.stereotype.JCoErrorListener;
import org.yanzx.core.extend.sap.jco.stereotype.JCoExceptionListener;
import org.yanzx.core.extend.sap.jco.stereotype.JCoFunctionHandler;
import org.yanzx.core.extend.sap.jco.stereotype.JCoStateChangedListener;
import org.yanzx.core.spring.util.AnnotationUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 11:45
 */
public abstract class AbstractJCoServerProxy implements JCoServer {

    /* Log. */
    protected static final Log _logger = LogFactory.getLog(JCoServer.class);

    /* Real JCo Server. */
    protected com.sap.conn.jco.server.JCoServer _jcoServer;

    /* _serverConfig. */
    protected final JCoServerConfig _serverConfig;
    public JCoServerConfig getJCoServerConfig() {
        return _serverConfig;
    }

    protected AbstractJCoServerProxy(JCoServerConfig _serverConfig) {
        if (_serverConfig == null) throw new JCoServerStartOnErrorSemaphore("Could not find jcoServer config.");
        this._serverConfig = _serverConfig;

        /* Distribute an unique server or destination if it is empty. */
        if (StringUtils.isEmpty(this._serverConfig.getServerName())) {
            this._serverConfig.setServerName(String.valueOf(IdentifierGenerator.build()));
        }
        if (StringUtils.isEmpty(this._serverConfig.getDestinationName())) {
            this._serverConfig.setDestinationName(String.valueOf(IdentifierGenerator.build()));
        }
    }

    /* ============================================================================================================= */

    /* Thread safe init flag. */
    protected volatile AtomicBoolean _isInit = new AtomicBoolean(false);

    /**
     * Init jcoServer.
     * @param _factory _factory
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    protected void init(JCoBeanFactory _factory) throws JCoServerStartOnErrorSemaphore {

        if (!_isInit.compareAndSet(false, true))  {
            _logger.info("JCoServer: [" + _serverConfig.getServerName() + "] has been initialized.");
            return;
        }

        if (_factory == null)
            throw new JCoServerStartOnErrorSemaphore("Could not find jco factory.");

        /* 打开调试 */
        if (_logger.isDebugEnabled()) JCo.setTrace(4, null);

        try {
            /* Init. */
            initJco(_serverConfig);

            /* Get real jcoServer instance. */
            _jcoServer = JCoServerFactory.getServer(_serverConfig.getServerName());

            /* Set _factory */
            _jcoServer.setCallHandlerFactory(new DefaultServerHandlerFactory.FunctionHandlerFactory());

            /* Registry repository. */
            registryRepository(_jcoServer, _serverConfig.getDestinationName(),
                    _factory.getBeans(DynamicFunctionHandler.class));

            /* Registry functionHandler. */
            registryJCoServerFunctionHandlers(_jcoServer,
                    _factory.getBeans(JCoServerFunctionHandler.class));

            /* Registry error listeners. */
            registryErrorListeners(_jcoServer,
                    _factory.getBeans(JCoServerErrorListener.class));

            /* Registry exception listeners. */
            registryExceptionListeners(_jcoServer,
                    _factory.getBeans(JCoServerExceptionListener.class));

            /* Registry state changed listeners. */
            registryStateChangedListeners(_jcoServer,
                    _factory.getBeans(JCoServerStateChangedListener.class));

            /* Registry JCoServer. */
            _factory.registry(_serverConfig.getServerName(), this);

        }catch (Exception _ex) { throw new JCoServerStartOnErrorSemaphore("Unable to create the server: [" + _serverConfig.getServerName() + "]", _ex); }
    }

    /* ============================================================================================================= */

    /**
     * Start jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    public void start() throws JCoServerStartOnErrorSemaphore {
        if (!_isInit.get())
            throw new JCoClientInvokeOnErrorSemaphore("JCoServer [" + _serverConfig.getServerName() + "] hasn't been initialized yet.");

        _jcoServer.start();
    }

    /**
     * Stop jcoServer.
     * @throws JCoServerStartOnErrorSemaphore JCoServerStartOnErrorSemaphore
     */
    public void stop() throws JCoServerStartOnErrorSemaphore {
        _jcoServer.stop();
    }

    /**
     * Get jcoServer state.
     * @return JCoServerState
     */
    public JCoServerState getState() {
        return _jcoServer.getState();
    }

    /**
     * Get real jcoServer.
     * @return JCoServer
     */
    public com.sap.conn.jco.server.JCoServer getRealJCoServer() {
        return _jcoServer;
    }


    /* ============================================================================================================= */

    /**
     * Registry repository.
     * @param _jcoServer _jcoServer
     * @param _destinationName _destinationName
     * @param _handlers _handlers
     */
    private static void registryRepository(com.sap.conn.jco.server.JCoServer _jcoServer, String _destinationName, List<DynamicFunctionHandler> _handlers) {
        if (_handlers != null && _handlers.size() > 0) {

            /* Create _repository. */
            JCoCustomRepository _customizeRepository = JCo
                    .createCustomRepository(_destinationName);

            for (DynamicFunctionHandler _handler : _handlers) {
                _customizeRepository.addFunctionTemplateToCache(_handler.registrySapFunc());
            }

            _jcoServer.setRepository(_customizeRepository);
        }
    }

    /* ============================================================================================================= */

    /**
     * Registry function handlers.
     * @param _jcoServer _jcoServer
     * @param _proxyHandlers _handlers
     */
    private static void registryJCoServerFunctionHandlers(com.sap.conn.jco.server.JCoServer _jcoServer, List<JCoServerFunctionHandler> _proxyHandlers) {

        /* Get factory. */
        DefaultServerHandlerFactory.FunctionHandlerFactory _factory = (DefaultServerHandlerFactory.FunctionHandlerFactory) _jcoServer.getCallHandlerFactory();

        for (JCoServerFunctionHandler _proxyHandler : _proxyHandlers) {

            /* Get real object. */
            JCoServerFunctionHandler _realHandler = (JCoServerFunctionHandler) AopTargetUtils.getTarget(_proxyHandler);

            /* Get JCoFunctionHandler */
            JCoFunctionHandler _annotation = AnnotationUtils.findMergedAnnotation(_realHandler.getClass(), JCoFunctionHandler.class);

            if (_annotation == null)
                throw new JCoServerStartOnErrorSemaphore("@JCoFunctionHandler could not be found in [" + _realHandler.getClass() + "]");

            if (StringUtils.isEmpty(_annotation.value()))
                throw new JCoServerStartOnErrorSemaphore("Function name could not be found in [" + _realHandler.getClass() + "]");

            /* Registry callback function. */
            _factory.registerHandler(_annotation.value(), _proxyHandler);
        }
    }

    /**
     * Registry error listeners.
     * @param _jcoServer jcoServer
     * @param _proxyListeners _listeners
     */
    private static void registryErrorListeners(com.sap.conn.jco.server.JCoServer _jcoServer, List<JCoServerErrorListener> _proxyListeners) {
        for (JCoServerErrorListener _proxyListener : _proxyListeners) {

            /* Get real object. */
            JCoServerErrorListener _realListener = (JCoServerErrorListener) AopTargetUtils.getTarget(_proxyListener);

            /* Get JCoErrorListener */
            JCoErrorListener _annotation = AnnotationUtils.findMergedAnnotation(_realListener.getClass(), JCoErrorListener.class);

            if (_annotation == null)
                throw new JCoServerStartOnErrorSemaphore("@JCoErrorListener could not be found in [" + _realListener.getClass() + "]");

            /* Registry */
            _jcoServer.addServerErrorListener(_proxyListener);
        }
    }

    /**
     * Registry exception listeners.
     * @param _jcoServer jcoServer
     * @param _proxyListeners _listeners
     */
    private static void registryExceptionListeners(com.sap.conn.jco.server.JCoServer _jcoServer, List<JCoServerExceptionListener> _proxyListeners) {
        for (JCoServerExceptionListener _proxyListener : _proxyListeners) {

            /* Get real object. */
            JCoServerExceptionListener _realListener = (JCoServerExceptionListener) AopTargetUtils.getTarget(_proxyListener);

            /* Get JCoExceptionListener */
            JCoExceptionListener _annotation = AnnotationUtils.findMergedAnnotation(_realListener.getClass(), JCoExceptionListener.class);

            if (_annotation == null)
                throw new JCoServerStartOnErrorSemaphore("@JCoExceptionListener could not be found in [" + _realListener.getClass() + "]");

            /* Registry */
            _jcoServer.addServerExceptionListener(_proxyListener);
        }
    }

    /**
     * Registry state changed listeners.
     * @param _jcoServer jcoServer
     * @param _proxyListeners _listeners
     */
    private static void registryStateChangedListeners(com.sap.conn.jco.server.JCoServer _jcoServer, List<JCoServerStateChangedListener> _proxyListeners) {
        for (JCoServerStateChangedListener _proxyListener : _proxyListeners) {

            /* Get real object. */
            JCoServerStateChangedListener _realListener = (JCoServerStateChangedListener) AopTargetUtils.getTarget(_proxyListener);

            /* Get JCoStateChangedListener */
            JCoStateChangedListener _annotation = AnnotationUtils.findMergedAnnotation(_realListener.getClass(), JCoStateChangedListener.class);

            if (_annotation == null)
                throw new JCoServerStartOnErrorSemaphore("@JCoStateChangedListener could not be found in [" + _realListener.getClass() + "]");

            /* Registry */
            _jcoServer.addServerStateChangedListener(_proxyListener);
        }
    }

    /* ============================================================================================================= */

    /**
     * Init jco.
     * @param _config sapConfig.
     */
    private static void initJco(JCoServerConfig _config) {

        /* Init connectionPool */
        initConnectionPool(_config);

        /* Init jcoServer. */
        initJcoServer(_config);
    }


    /* ============================================================================================================= */

    /**
     * Init connectionPool.
     * @param _config sapConfig.
     */
    private static void initConnectionPool(JCoServerConfig _config) throws JCoServerStartOnErrorSemaphore {

        /* Add _destination config. */
        CustomizeDestinationDataProvider.getInstance().registryDestination(_config);
    }

    /**
     * Init jcoServer.
     * @param _config sapConfig.
     */
    private static void initJcoServer(JCoServerConfig _config) {

        /* Add _server config. */
        CustomizeServerDataProvider.getInstance().registryServer(_config);
    }
}
