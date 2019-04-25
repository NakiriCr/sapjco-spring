package org.yanzx.core.extend.sap.jco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerState;
import com.sap.conn.jco.server.JCoServerStateChangedListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yanzx.core.extend.sap.jco.stereotype.JCoStateChangedListener;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 22:26
 */
@JCoStateChangedListener
public class DefaultJCoStateChangedListener implements JCoServerStateChangedListener {

    private static final Log _logger = LogFactory.getLog(DefaultJCoStateChangedListener.class);

    @Override
    public void serverStateChangeOccurred(JCoServer _server, JCoServerState _oldState, JCoServerState _newState) {
        _logger.info("StateChange on JCoServer: [" + _server.getProgramID() + "] | State: [" + _oldState + "] ==> [" + _newState + "]");
    }
}
