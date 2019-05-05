package cn.gitlab.virtualcry.jcospring.connect.server;

import cn.gitlab.virtualcry.jcospring.connect.config.JCoDataProvider;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoSettings;
import com.sap.conn.jco.JCo;
import com.sap.conn.jco.JCoCustomRepository;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.server.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.JCoBeanFactoryProvider;
import cn.gitlab.virtualcry.jcospring.connect.beans.factory.JCoBeanFactory;
import cn.gitlab.virtualcry.jcospring.connect.server.handler.DynamicFunctionHandler;
import cn.gitlab.virtualcry.jcospring.connect.server.semaphore.JCoServerCreatedOnErrorSemaphore;

import java.util.List;
import java.util.Map;

import static com.sap.conn.jco.server.DefaultServerHandlerFactory.FunctionHandlerFactory;

/**
 * JCo Server
 *
 * @author VirtualCry
 */
public class DefaultJCoServer implements JCoServer {
    private static final Log logger = LogFactory.getLog(JCoServer.class);

    // jco server
    private final com.sap.conn.jco.server.JCoServer realServer;

    // jco settings
    private final JCoSettings settings;

    public DefaultJCoServer(JCoSettings settings) {
        // open trace if is debug
        if (logger.isDebugEnabled())
            JCo.setTrace(4, null);

        // settings
        this.settings = settings;
        // server
        this.realServer = createJCoServer(this.settings);
    }

    @Override
    public void start() {
        realServer.start();
    }

    @Override
    public void stop() {
        realServer.stop();
    }

    @Override
    public void release() {
        realServer.stop();
        JCoDataProvider.getSingleton()
                .unRegisterServerSettings(settings.getSettingsName());
    }

    @Override
    public JCoSettings getSettings() {
        return settings;
    }

    @Override
    public com.sap.conn.jco.server.JCoServer getRealServer() {
        return realServer;
    }

    @Override
    public JCoServerState getState() {
        return realServer.getState();
    }

    /* =============================================================================== */

    /**
     * init server
     * @param settings settings
     */
    private static com.sap.conn.jco.server.JCoServer createJCoServer(JCoSettings settings) {
        // get bean factory
        JCoBeanFactory beanFactory = JCoBeanFactoryProvider.getIfAvailable();

        // register server
        JCoDataProvider.getSingleton()
                .registerServerSettings(settings);

        try {
            // get server
            com.sap.conn.jco.server.JCoServer server = JCoServerFactory.getServer(settings.getSettingsName());

            // use callback factory
            server.setCallHandlerFactory(new FunctionHandlerFactory());

            // register dynamic function
            registerDynamicSapFunctions(server, settings.getSettingsName(),
                    beanFactory.getBeans(DynamicFunctionHandler.class));

            // register callback function handlers
            registerCallbackFunctionHandlers(server,
                    beanFactory.getBeansOfType(JCoServerFunctionHandler.class));

            // register error listeners
            registerErrorListeners(server,
                    beanFactory.getBeans(JCoServerErrorListener.class));
            // register exception listeners
            registerExceptionListeners(server,
                    beanFactory.getBeans(JCoServerExceptionListener.class));
            // register exception listeners
            registerStateChangedListeners(server,
                    beanFactory.getBeans(JCoServerStateChangedListener.class));

            return server;

        }catch (JCoException ex) { throw new JCoServerCreatedOnErrorSemaphore(
                "Unable to create the server: [" + settings.getSettingsName() + "]", ex); }
    }

    private static void registerDynamicSapFunctions(com.sap.conn.jco.server.JCoServer server,
                                                    String destinationName,
                                                    List<DynamicFunctionHandler> registerHandlers) {
        if (registerHandlers != null && registerHandlers.size() > 0) {
            // create
            JCoCustomRepository customizeRepository = JCo
                    .createCustomRepository(destinationName);
            // add function definition
            registerHandlers.forEach(handler -> customizeRepository
                    .addFunctionTemplateToCache(handler.registerSapFunc()));
            // set
            server.setRepository(customizeRepository);
        }
    }

    private static void registerCallbackFunctionHandlers(com.sap.conn.jco.server.JCoServer server,
                                                         Map<String, JCoServerFunctionHandler> callbackFuncHandlers) {
        if (callbackFuncHandlers != null && callbackFuncHandlers.size() > 0) {
            // get jco callback factory
            FunctionHandlerFactory funcHandlerFactory = (FunctionHandlerFactory)
                    server.getCallHandlerFactory();
            // register callback function
            callbackFuncHandlers.forEach(funcHandlerFactory::registerHandler);
        }
    }

    private static void registerErrorListeners(com.sap.conn.jco.server.JCoServer server,
                                               List<JCoServerErrorListener> errorListeners) {
        if (errorListeners != null && errorListeners.size() > 0) {
            // add jco error listener
            errorListeners.forEach(server::addServerErrorListener);
        }
    }

    private static void registerExceptionListeners(com.sap.conn.jco.server.JCoServer server,
                                                   List<JCoServerExceptionListener> exceptionListeners) {
        if (exceptionListeners != null && exceptionListeners.size() > 0) {
            // add jco exception listener
            exceptionListeners.forEach(server::addServerExceptionListener);
        }
    }

    private static void registerStateChangedListeners(com.sap.conn.jco.server.JCoServer server,
                                                      List<JCoServerStateChangedListener> stateChangedListeners) {
        if (stateChangedListeners != null && stateChangedListeners.size() > 0) {
            // add jco state changed listener
            stateChangedListeners.forEach(server::addServerStateChangedListener);
        }
    }
}
