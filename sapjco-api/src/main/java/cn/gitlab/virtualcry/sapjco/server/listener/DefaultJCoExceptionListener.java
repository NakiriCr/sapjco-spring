package cn.gitlab.virtualcry.sapjco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerExceptionListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implement of {@link JCoServerExceptionListener}
 *
 * @author VirtualCry
 */
@Slf4j
public class DefaultJCoExceptionListener implements JCoServerExceptionListener {

    @Override
    public void serverExceptionOccurred(JCoServer server, String connectionId,
                                        JCoServerContextInfo serverCtx, Exception ex) {

        log.error("Error on JCoServer: [" + server.getProgramID() + "] "
                + "| Connection: [" + connectionId + "]", ex);
    }
}
