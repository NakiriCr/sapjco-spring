package org.yanzx.core.extend.sap.jco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default listener for exception messages:
 *
 * @author VirtualCry
 */
public class DefaultJCoExceptionListener implements JCoServerExceptionListener {

    private static final Log logger =
            LogFactory.getLog(DefaultJCoExceptionListener.class);


    @Override
    public void serverExceptionOccurred(JCoServer server, String connectionId,
                                        JCoServerContextInfo serverCtx, Exception ex) {

        logger.error("Error on JCoServer: [" +
                server.getProgramID() + "] | Connection: [" + connectionId + "]", ex);


    }
}
