package cn.gitlab.virtualcry.sapjco.beans.factory;

import cn.gitlab.virtualcry.sapjco.client.DefaultJCoClient;
import cn.gitlab.virtualcry.sapjco.client.JCoClient;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.config.JCoSettings;
import cn.gitlab.virtualcry.sapjco.server.DefaultJCoServer;
import cn.gitlab.virtualcry.sapjco.server.JCoServer;
import cn.gitlab.virtualcry.sapjco.server.semaphore.JCoServerCreatedOnErrorSemaphore;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static cn.gitlab.virtualcry.sapjco.config.Connections.SERVER;

/**
 * Default implement of {@link JCoConnectionFactory}
 *
 * @author VirtualCry
 */
public class DefaultJCoConnectionFactory implements JCoConnectionFactory {

    private final Map<String, JCoClient>                clients;
    private final Map<String, JCoServer>                servers;

    public DefaultJCoConnectionFactory() {
        this.clients = new ConcurrentHashMap<>();
        this.servers = new ConcurrentHashMap<>();
    }


    @Override
    public JCoClient createClient(String clientName, JCoSettings settings) {
        if (clientName == null || "".equals(clientName))
            throw new JCoClientCreatedOnErrorSemaphore("Could not find client name.");
        if (settings == null)
            throw new JCoClientCreatedOnErrorSemaphore("Could not find jco settings.");

        return this.clients.compute(clientName, (key, client) -> {
            if (client != null)
                throw new JCoClientCreatedOnErrorSemaphore(
                        "Client: [" + key + "] is existed. Not allow the same client name to create.");
            return new DefaultJCoClient(settings);
        });
    }

    @Override
    public JCoClient getClient(String clientName) {
        return this.clients.get(clientName);
    }

    @Override
    public JCoClient getOrCreateClient(String clientName, JCoSettings settings) {
        return Optional
                .ofNullable(this.getClient(clientName))
                .orElseGet(() -> this.createClient(clientName, settings));
    }

    @Override
    public Map<String, JCoClient> getClients() {
        return Collections.unmodifiableMap(this.clients);
    }

    @Override
    public JCoServer createServer(String serverName, JCoSettings settings) {
        if (serverName == null || "".equals(serverName))
            throw new JCoServerCreatedOnErrorSemaphore("Could not find server name.");
        if (settings == null)
            throw new JCoServerCreatedOnErrorSemaphore("Could not find jco settings.");

        // duplicate check.
        this.servers.entrySet().stream()
                .filter(entry -> settings.getUniqueKey(SERVER).equals(entry.getValue().getSettings().getUniqueKey(SERVER)))
                .findFirst()
                .ifPresent(entry -> {
                    throw new JCoServerCreatedOnErrorSemaphore("Duplicate settings: [" +
                            serverName + "] with server: [" + entry.getKey() + "]");
                });

        return this.servers.compute(serverName, (key, server) -> {
            if (server != null)
                throw new JCoServerCreatedOnErrorSemaphore(
                        "Server: [" + key + "] is existed. Not allow the same server name to create.");
            return new DefaultJCoServer(settings);
        });
    }

    @Override
    public JCoServer getServer(String serverName) {
        return this.servers.get(serverName);
    }

    @Override
    public JCoServer getOrCreateServer(String serverName, JCoSettings settings) {
        return Optional
                .ofNullable(this.getServer(serverName))
                .orElseGet(() -> this.createServer(serverName, settings));
    }

    @Override
    public Map<String, JCoServer> getServers() {
        return Collections.unmodifiableMap(servers);
    }

    @Override
    public void releaseClient(String clientName) {
        Optional.ofNullable(this.clients.remove(clientName))
                .ifPresent(JCoClient::release);
    }

    @Override
    public void releaseClients() {
        this.clients.values().forEach(JCoClient::release);
        this.clients.clear();
    }

    @Override
    public void releaseServer(String serverName) {
        Optional.ofNullable(this.servers.remove(serverName))
                .ifPresent(JCoServer::release);
    }

    @Override
    public void releaseServers() {
        this.servers.values().forEach(JCoServer::release);
        this.servers.clear();
    }

    @Override
    public String getServerName(String settingUniqueKey) {
        return this.servers.entrySet().stream()
                .filter(entry -> entry.getValue().getSettings().getUniqueKey(SERVER).equals(settingUniqueKey))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
