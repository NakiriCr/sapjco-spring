package org.yanzx.core.extend.sap.jco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerState;
import com.sap.conn.jco.server.JCoServerStateChangedListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default listener for changed messages:
 *
 * @author VirtualCry
 */
public class DefaultJCoStateChangedListener implements JCoServerStateChangedListener {

    private static final Log logger =
            LogFactory.getLog(DefaultJCoStateChangedListener.class);


    @Override
    public void serverStateChangeOccurred(JCoServer server,
                                          JCoServerState oldState, JCoServerState newState) {

        logger.info("StateChange on JCoServer: [" +
                server.getProgramID() + "] | State: [" + oldState + "] ==> [" + newState + "]");


    }
}
