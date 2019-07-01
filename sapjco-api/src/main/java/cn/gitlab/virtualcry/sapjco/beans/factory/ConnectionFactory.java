package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.client.DefaultJCoClient;
import cn.gitlab.virtualcry.sapjco.client.JCoClient;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.DefaultJCoServer;
import cn.gitlab.virtualcry.sapjco.server.JCoServer;
import cn.gitlab.virtualcry.sapjco.server.semaphore.JCoServerCreatedOnErrorSemaphore;

import java.util.UUID;

/**
 * RFC connection factory.
 *
 * @author VirtualCry
 */
public class ConnectionFactory {

    /**
     * Create a new {@link JCoClient} using the given {@link JCoSettings}
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoClient}
     */
    public static JCoClient createClient(JCoSettings settings) {
        if (settings == null)
            throw new JCoClientCreatedOnErrorSemaphore("Could not find jco settings.");

        settings.setSettingsName(UUID.randomUUID().toString());
        return new DefaultJCoClient(settings);
    }


    /**
     * Create a new {@link JCoServer} using the given {@link JCoSettings}
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoServer}
     */
    public static JCoServer createServer(JCoSettings settings) {
        if (settings == null)
            throw new JCoServerCreatedOnErrorSemaphore("Could not find jco settings.");

        settings.setSettingsName(UUID.randomUUID().toString());
        return new DefaultJCoServer(settings);
    }

}
