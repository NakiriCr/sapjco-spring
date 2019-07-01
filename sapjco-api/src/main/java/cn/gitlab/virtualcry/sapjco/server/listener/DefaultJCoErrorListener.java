package cn.gitlab.virtualcry.sapjco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerContextInfo;
import com.sap.conn.jco.server.JCoServerErrorListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implement of {@link JCoServerErrorListener}
 *
 * @author VirtualCry
 */
@Slf4j
public class DefaultJCoErrorListener implements JCoServerErrorListener {

    @Override
    public void serverErrorOccurred(JCoServer server, String connectionId,
                                    JCoServerContextInfo serverCtx, Error error) {

        log.error("Error on JCoServer: [" + server.getProgramID() + "] "
                + "| Connection: [" + connectionId + "]", error);
    }
}
