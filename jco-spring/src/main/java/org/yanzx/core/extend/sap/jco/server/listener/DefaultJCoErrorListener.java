package org.yanzx.core.extend.sap.jco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yanzx.core.extend.sap.jco.stereotype.JCoErrorListener;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 22:22
 */
@JCoErrorListener
public class DefaultJCoErrorListener implements JCoServerErrorListener {

    private static final Log _logger = LogFactory.getLog(DefaultJCoErrorListener.class);

    @Override
    public void serverErrorOccurred(JCoServer _server, String _connectionId, JCoServerContextInfo _serverCtx, Error _er) {
        _logger.error("Error on JCoServer: [" + _server.getProgramID() + "] | Connection: [" + _connectionId + "]", _er);
    }
}
