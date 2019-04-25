package org.yanzx.core.extend.sap.jco.server.provider;

import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.ext.ServerDataEventListener;
import com.sap.conn.jco.ext.ServerDataProvider;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 22:43
 */
public class CustomizeServerDataProvider implements ServerDataProvider {

    private CustomizeServerDataProvider() { }
    private static CustomizeServerDataProvider _provider = new CustomizeServerDataProvider();
    public static CustomizeServerDataProvider getInstance() {
        return _provider;
    }

    static {
        /* Registry in environment. */
        Environment.registerServerDataProvider(CustomizeServerDataProvider.getInstance());
    }

    /* Store server properties. */
    private Map<String, Properties> _servers = new ConcurrentHashMap<>();

    @Override
    public void setServerDataEventListener(ServerDataEventListener _listener) {

    }

    @Override
    public Properties getServerProperties(String _serverName) {

        if (!_servers.containsKey(_serverName)) throw new JCoServerStartOnErrorSemaphore("Server : [" + _serverName + "] is not available.");

        return _servers.get(_serverName);
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }

    /**
     * Registry new server
     * @param _config _config
     */
    public void registryServer(JCoServerConfig _config) {

        if (_servers.containsKey(_config.getServerName()))
            throw new JCoServerStartOnErrorSemaphore("Server: [" + _config.getServerName() + "] has been already registered.");

        Properties _properties = new Properties();

        /* Server config. */
        _properties.setProperty(ServerDataProvider.JCO_GWHOST, _config.getGatewayHost());
        _properties.setProperty(ServerDataProvider.JCO_GWSERV, _config.getGatewayService());
        _properties.setProperty(ServerDataProvider.JCO_PROGID, _config.getProgramId());
        _properties.setProperty(ServerDataProvider.JCO_REP_DEST, _config.getDestinationName());
        _properties.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, _config.getConnectionCount());

        _servers.put(_config.getServerName(), _properties);
    }

    /**
     * Un registry server
     * @param _serverName _serverName
     */
    public void unRegistryServer(String _serverName) {
        _servers.remove(_serverName);
    }
}
