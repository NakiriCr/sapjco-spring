package cn.gitlab.virtualcry.sapjco.config;

import com.sap.conn.jco.ext.*;
import com.sap.conn.jco.rt.DefaultDestinationManager;
import com.sap.conn.jco.rt.StandaloneServerFactory;
import cn.gitlab.virtualcry.sapjco.client.semaphore.JCoClientCreatedOnErrorSemaphore;
import cn.gitlab.virtualcry.sapjco.server.semaphore.JCoServerCreatedOnErrorSemaphore;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JCo destination and server data provider
 *
 * @author VirtualCry
 */
public class JCoDataProvider implements DestinationDataProvider, ServerDataProvider {

    private final Map<String, Properties>       clientSettingsProviders;
    private final Map<String, Properties>       serverSettingsProviders;
    private DestinationDataEventListener        destinationDataEventListener;
    private ServerDataEventListener             serverDataEventListener;

    private static class JCoDataProviderInstance {
        private static final JCoDataProvider INSTANCE = new JCoDataProvider();
    }
    private JCoDataProvider() {
        this.clientSettingsProviders = new ConcurrentHashMap<>();
        this.serverSettingsProviders = new ConcurrentHashMap<>();
    }
    public static JCoDataProvider getSingleton() {  // singleton
        return JCoDataProviderInstance.INSTANCE;
    }


    @Override
    public Properties getDestinationProperties(String destinationName) {
        return Stream.concat(clientSettingsProviders.entrySet().stream(), serverSettingsProviders.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .computeIfAbsent(destinationName, key -> {
                    throw new JCoClientCreatedOnErrorSemaphore("Destination settings : [" + key + "] is not available.");
                });
    }

    @Override
    public Properties getServerProperties(String serverName) {
        return serverSettingsProviders.computeIfAbsent(serverName, key -> {
            throw new JCoServerCreatedOnErrorSemaphore("Server settings: [" + key + "] is not available.");
        });
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener destinationDataEventListener) {
        this.destinationDataEventListener = destinationDataEventListener;
    }

    @Override
    public void setServerDataEventListener(ServerDataEventListener serverDataEventListener) {
        this.serverDataEventListener = serverDataEventListener;
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }


    /* ============================================================================================================ */

    /**
     * Register provider in JCo runtime environment
     */
    public static void registerInEnvironment() {

        // register destination provider
        Environment.registerDestinationDataProvider(
                JCoDataProviderInstance.INSTANCE);
        // register server provider
        Environment.registerServerDataProvider(
                JCoDataProviderInstance.INSTANCE);

    }


    /**
     * Register settings in destination for JCo client
     * @param settings connection settings
     * @see DefaultDestinationManager#getDestination(String)
     */
    public void registerClientSettings(JCoSettings settings) {
        clientSettingsProviders.compute(settings.getUniqueKey(Connections.CLIENT), (clientName, clientSettings) -> {
            // exist check
            if (clientSettings != null)
                throw new JCoClientCreatedOnErrorSemaphore("Destination: [" + clientName + "] has been already registered.");
            // refer
            return referDestinationData(settings);
        });
    }


    /**
     * Register settings in destination and server for JCo server
     * @param settings connection settings
     * @see StandaloneServerFactory#getServer(String)
     * @see DefaultDestinationManager#getDestination(String)
     */
    public void registerServerSettings(JCoSettings settings) {
        serverSettingsProviders.compute(settings.getUniqueKey(Connections.SERVER), (serverName, serverSettings) -> {
            // exist check
            if (serverSettings != null)
                throw new JCoServerCreatedOnErrorSemaphore("Server: [" + serverName + "] has been already registered.");
            // refer
            return referServerData(settings);
        });
    }


    /**
     * Un register destination for JCo client
     * @param settingsName name
     * @see DestinationDataEventListener#deleted(String)
     * @see DefaultDestinationManager#deleted(String)
     */
    public void unRegisterClientSettings(String settingsName) {
        clientSettingsProviders.remove(settingsName);
        destinationDataEventListener.deleted(settingsName);
    }


    /**
     * Un register destination and server for JCo server
     * @param settingsName name
     * @see ServerDataEventListener#deleted(String)
     * @see StandaloneServerFactory#deleted(String)
     * @see DestinationDataEventListener#deleted(String)
     * @see DefaultDestinationManager#deleted(String)
     */
    public void unRegisterServerSettings(String settingsName) {
        serverSettingsProviders.remove(settingsName);
        serverDataEventListener.deleted(settingsName);
        destinationDataEventListener.deleted(settingsName);
    }


    private static Properties referDestinationData(JCoSettings settings) {
        Properties provider = new Properties();

        // connection settings
        provider.setProperty(DestinationDataProvider.JCO_ASHOST, settings.getAshost());
        provider.setProperty(DestinationDataProvider.JCO_SYSNR, settings.getSysnr());
        provider.setProperty(DestinationDataProvider.JCO_CLIENT, settings.getClient());
        provider.setProperty(DestinationDataProvider.JCO_USER, settings.getUser());
        provider.setProperty(DestinationDataProvider.JCO_PASSWD, settings.getPassword());
        provider.setProperty(DestinationDataProvider.JCO_LANG, settings.getLanguage());

        // pool settings
        provider.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, settings.getPoolCapacity());
        provider.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, settings.getPeakLimit());

        return provider;
    }

    private static Properties referServerData(JCoSettings settings) {
        // refer destination
        Properties provider = referDestinationData(settings);

        // server settings
        provider.setProperty(ServerDataProvider.JCO_GWHOST, settings.getGatewayHost());
        provider.setProperty(ServerDataProvider.JCO_GWSERV, settings.getGatewayService());
        provider.setProperty(ServerDataProvider.JCO_PROGID, settings.getProgramId());
        provider.setProperty(ServerDataProvider.JCO_REP_DEST, settings.getUniqueKey(Connections.SERVER));
        provider.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, settings.getConnectionCount());

        return provider;
    }
}
