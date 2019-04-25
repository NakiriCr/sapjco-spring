package org.yanzx.core.extend.sap.jco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yanzx.core.extend.sap.jco.stereotype.JCoExceptionListener;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 22:25
 */
@JCoExceptionListener
public class DefaultJCoExceptionListener implements JCoServerExceptionListener {

    private static final Log _logger = LogFactory.getLog(DefaultJCoExceptionListener.class);

    @Override
    public void serverExceptionOccurred(JCoServer _server, String _connectionId, JCoServerContextInfo _serverCtx, Exception _ex) {
        _logger.error("Error on JCoServer: [" + _server.getProgramID() + "] | Connection: [" + _connectionId + "]", _ex);
    }
}
