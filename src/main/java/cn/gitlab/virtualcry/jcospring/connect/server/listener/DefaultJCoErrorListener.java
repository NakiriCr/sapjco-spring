package cn.gitlab.virtualcry.jcospring.connect.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Default listener for error messages
 *
 * @author VirtualCry
 */
public class DefaultJCoErrorListener implements JCoServerErrorListener {

    private static final Log logger =
            LogFactory.getLog(DefaultJCoErrorListener.class);


    @Override
    public void serverErrorOccurred(JCoServer server, String connectionId,
                                    JCoServerContextInfo serverCtx, Error error) {

        logger.error("Error on JCoServer: [" +
                server.getProgramID() + "] | Connection: [" + connectionId + "]", error);


    }
}
