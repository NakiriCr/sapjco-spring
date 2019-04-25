package org.yanzx.core.extend.sap.jco.server.provider;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.server.semaphore.JCoServerStartOnErrorSemaphore;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 22:29
 */
public class CustomizeDestinationDataProvider implements DestinationDataProvider {

    private CustomizeDestinationDataProvider() { }
    private static CustomizeDestinationDataProvider _provider = new CustomizeDestinationDataProvider();
    public static CustomizeDestinationDataProvider getInstance() {
        return _provider;
    }

    static {
        /* Registry in environment. */
        Environment.registerDestinationDataProvider(CustomizeDestinationDataProvider.getInstance());
    }

    /* Store connect properties. */
    private Map<String, Properties> _destinations = new ConcurrentHashMap<>();

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener _listener) {

    }

    @Override
    public Properties getDestinationProperties(String _destinationName) {

        if (!_destinations.containsKey(_destinationName)) throw new JCoServerStartOnErrorSemaphore("Destination : [" + _destinationName + "] is not available.");

        return _destinations.get(_destinationName);
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }

    /**
     * Registry new destination
     * @param _config _config
     */
    public void registryDestination(JCoServerConfig _config) {

        if (_destinations.containsKey(_config.getDestinationName()))
            throw new JCoServerStartOnErrorSemaphore("Destination: [" + _config.getDestinationName() + "] has been already registered.");

        Properties _properties = new Properties();

        /* Connect config. */
        _properties.setProperty(DestinationDataProvider.JCO_ASHOST, _config.getAshost());
        _properties.setProperty(DestinationDataProvider.JCO_SYSNR, _config.getSysnr());
        _properties.setProperty(DestinationDataProvider.JCO_CLIENT, _config.getClient());
        _properties.setProperty(DestinationDataProvider.JCO_USER, _config.getUser());
        _properties.setProperty(DestinationDataProvider.JCO_PASSWD, _config.getPassword());
        _properties.setProperty(DestinationDataProvider.JCO_LANG, _config.getLanguage());

        /* ConnectionPool config.  */
        _properties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, _config.getPoolCapacity());
        _properties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, _config.getPeakLimit());

        _destinations.put(_config.getDestinationName(), _properties);
    }

    /**
     * Un registry destination
     * @param _destinationName _destinationName
     */
    public void unRegistryDestination(String _destinationName) {
        _destinations.remove(_destinationName);
    }
}
