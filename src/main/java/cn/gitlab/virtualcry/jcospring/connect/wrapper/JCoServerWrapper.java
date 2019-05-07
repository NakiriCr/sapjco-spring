package cn.gitlab.virtualcry.jcospring.connect.wrapper;

import com.sap.conn.jco.server.JCoServerState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoSettings;
import cn.gitlab.virtualcry.jcospring.connect.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.server.DefaultJCoServer;
import cn.gitlab.virtualcry.jcospring.connect.server.JCoServer;

import java.util.UUID;

/**
 * JCo Server wrapper
 *
 * @author VirtualCry
 */
public class JCoServerWrapper implements JCoServer {
    private static final Log logger = LogFactory.getLog(JCoServer.class);

    // server
    private final JCoServer server;

    public JCoServerWrapper(JCoSettings settings) {
        if (settings == null)
            throw new JCoClientCreatedOnErrorSemaphore("Could not find jco settings.");

        // distribute an unique name
        settings.setSettingsName(UUID.randomUUID().toString());

        this.server = new DefaultJCoServer(settings);
    }

    @Override
    public void release() {
        server.release();

        if (logger.isDebugEnabled())
            logger.debug("JCoServer: [" + getSettings().getSettingsName() + "] released.");
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void stop() {
        server.stop();
    }

    @Override
    public JCoSettings getSettings() {
        return server.getSettings();
    }

    @Override
    public com.sap.conn.jco.server.JCoServer getRealServer() {
        return server.getRealServer();
    }

    @Override
    public JCoServerState getState() {
        return server.getState();
    }
}