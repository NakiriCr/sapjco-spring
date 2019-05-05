package cn.gitlab.virtualcry.jcospring.connect.wrapper;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoSettings;
import cn.gitlab.virtualcry.jcospring.connect.client.DefaultJCoClient;
import cn.gitlab.virtualcry.jcospring.connect.client.handler.FunctionRequestHandler;
import cn.gitlab.virtualcry.jcospring.connect.client.handler.FunctionResponseHandler;
import cn.gitlab.virtualcry.jcospring.connect.client.semaphore.JCoClientInvokeOnErrorSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.client.JCoClient;

import java.util.UUID;

/**
 * JCo Client wrapper
 *
 * @author VirtualCry
 */
public class JCoClientWrapper implements JCoClient, AutoCloseable {

    private static final Log logger =
            LogFactory.getLog(JCoClient.class);


    // client
    private final JCoClient client;

    public JCoClientWrapper(JCoSettings settings) {
        if (settings == null)
            throw new IllegalArgumentException("Could not find jco settings.");

        // distribute an unique name
        settings.setSettingsName(UUID.randomUUID().toString());

        this.client = new DefaultJCoClient(settings);
    }


    @Override
    public void release() {
        // destroy
        client.release();

        if (logger.isDebugEnabled())
            logger.debug("JCoClient: [" + getSettings().getSettingsName() + "] released.");
    }

    @Override
    public JCoSettings getSettings() {
        return client.getSettings();
    }

    @Override
    public JCoDestination getDestination() throws JCoClientInvokeOnErrorSemaphore {
        return client.getDestination();
    }

    @Override
    public JCoFunction getFunction(String functionName) throws JCoClientInvokeOnErrorSemaphore {
        return client.getFunction(functionName);
    }

    @Override
    public void invokeSapFunc(String functionName,
                              FunctionRequestHandler requestHandler,
                              FunctionResponseHandler responseHandler) throws JCoClientInvokeOnErrorSemaphore {
        client.invokeSapFunc(functionName, requestHandler, responseHandler);
    }


    @Override
    public void close() throws Exception {
        release();
    }
}
