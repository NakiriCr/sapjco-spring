package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.client.JCoClient;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.JCoServer;

import java.util.Map;

/**
 * RFC connection factory.
 *
 * @author VirtualCry
 * @since 3.2.3
 */
public interface JCoConnectionFactory {

    /**
     * Create a new {@link JCoClient} using the given {@link JCoSettings}
     * @param clientName The {@literal clientName} to be used to cache.
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoClient}
     */
    JCoClient createClient(String clientName, JCoSettings settings);


    /**
     * Get a {@link JCoClient} using the given {@literal clientName}
     * @param clientName The {@literal clientName} to be used to matching.
     * @return  A cache {@link JCoClient}
     */
    JCoClient getClient(String clientName);


    /**
     * Get or create a new {@link JCoClient} using the given {@link JCoSettings}
     * @param clientName The {@literal clientName} to be used to cache.
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoClient}
     */
    JCoClient getOrCreateClient(String clientName, JCoSettings settings);


    /**
     * Get all cache {@link JCoClient}s
     * @return All cache {@link JCoClient}s
     */
    Map<String, JCoClient> getClients();


    /**
     * Create a new {@link JCoServer} using the given {@link JCoSettings}
     * @param serverName The {@literal serverName} to be used to cache.
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoServer}
     */
    JCoServer createServer(String serverName, JCoSettings settings);


    /**
     * Get a {@link JCoServer} using the given {@literal clientName}
     * @param serverName The {@literal serverName} to be used to matching.
     * @return  A cache {@link JCoServer}
     */
    JCoServer getServer(String serverName);


    /**
     * Get or create a new {@link JCoServer} using the given {@link JCoSettings}
     * @param serverName The {@literal serverName} to be used to cache.
     * @param settings The {@link JCoSettings} to be used.
     * @return A new {@link JCoServer}
     */
    JCoServer getOrCreateServer(String serverName, JCoSettings settings);


    /**
     * Get all cache {@link JCoServer}s
     * @return All cache {@link JCoServer}s
     */
    Map<String, JCoServer> getServers();


    /**
     * Release {@link JCoClient}
     * @param clientName The {@literal clientName} to be used to release.
     */
    void releaseClient(String clientName);


    /**
     * Release all {@link JCoClient}s
     */
    void releaseClients();


    /**
     * Release {@link JCoServer}
     * @param serverName The {@literal serverName} to be used to release.
     */
    void releaseServer(String serverName);


    /**
     * Release all {@link JCoServer}s
     */
    void releaseServers();


    /**
     * Get {@literal serverName} using the given {@literal clientName}
     * @param settingUniqueKey The {@literal settingUniqueKey} to be used to matching.
     * @return The {@literal serverName}.
     */
    String getServerName(String settingUniqueKey);
}
