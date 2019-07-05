package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.client.DefaultJCoClient;
import cn.gitlab.virtualcry.sapjco.client.JCoClient;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.DefaultJCoServer;
import cn.gitlab.virtualcry.sapjco.server.JCoServer;
import cn.gitlab.virtualcry.sapjco.server.semaphore.JCoServerCreatedOnErrorSemaphore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RFC connection factory.
 *
 * @author VirtualCry
 */
public class ConnectionFactory {

    private static final Map<String, JCoClient>         clients;
    private static final Map<String, JCoServer>         servers;

    static {
        clients = new ConcurrentHashMap<>();
        servers = new ConcurrentHashMap<>();
    }


    /**
     * Create a new {@link JCoClient} using the given {@link JCoSettings}
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoClient}
     */
    public static JCoClient createClient(JCoSettings settings) {
        if (settings == null)
            throw new JCoClientCreatedOnErrorSemaphore("Could not find jco settings.");

        return clients.computeIfAbsent(settings.getSettingsName(), key -> new DefaultJCoClient(settings));
    }


    /**
     * Create a new {@link JCoServer} using the given {@link JCoSettings}
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoServer}
     */
    public static JCoServer createServer(JCoSettings settings) {
        if (settings == null)
            throw new JCoServerCreatedOnErrorSemaphore("Could not find jco settings.");

        settings.setSettingsName(
                settings.getServerUniqueKey()
        );
        return servers.computeIfAbsent(settings.getSettingsName(), key -> new DefaultJCoServer(settings));
    }


    /**
     * Release client connection.
     * @param settings The {@link JCoSettings} to be used to release..
     */
    public static void releaseClient(JCoSettings settings) {
        clients.remove(settings.getSettingsName())
                .release();
    }


    /**
     * Release client connection.
     * @param settings The {@link JCoSettings} to be used to release..
     */
    public static void releaseServer(JCoSettings settings) {
        servers.remove(settings.getSettingsName())
                .release();
    }

}
