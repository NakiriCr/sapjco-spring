package cn.gitlab.virtualcry.jcospring.connect.beans.factory;

import cn.gitlab.virtualcry.jcospring.connect.client.DefaultJCoClient;
import cn.gitlab.virtualcry.jcospring.connect.client.JCoClient;
import cn.gitlab.virtualcry.jcospring.connect.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.jcospring.connect.config.JCoSettings;
import cn.gitlab.virtualcry.jcospring.connect.server.DefaultJCoServer;
import cn.gitlab.virtualcry.jcospring.connect.server.JCoServer;

import java.util.UUID;

/**
 * JCo connection factory.
 *
 * @author VirtualCry
 */
public class ConnectionFactory {

    /**
     * Create jco client.
     * @param settings settings
     * @return JCoClient
     */
    public static JCoClient createClient(JCoSettings settings) {
        if (settings == null)
            throw new IllegalArgumentException("Could not find jco settings.");

        // distribute an unique name
        settings.setSettingsName(UUID.randomUUID().toString());

        // create client.
        return new DefaultJCoClient(settings);
    }

    /**
     * Create jco server.
     * @param settings settings
     * @return JCoServer
     */
    public static JCoServer createServer(JCoSettings settings) {
        if (settings == null)
            throw new JCoClientCreatedOnErrorSemaphore("Could not find jco settings.");

        // distribute an unique name
        settings.setSettingsName(UUID.randomUUID().toString());

        // create server.
        return new DefaultJCoServer(settings);
    }

}
