package cn.gitlab.virtualcry.sapjco.server.listener;

import com.sap.conn.jco.server.JCoServer;
import com.sap.conn.jco.server.JCoServerState;
import com.sap.conn.jco.server.JCoServerStateChangedListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implement of {@link JCoServerStateChangedListener}
 *
 * @author VirtualCry
 */
@Slf4j
public class DefaultJCoStateChangedListener implements JCoServerStateChangedListener {

    @Override
    public void serverStateChangeOccurred(JCoServer server,
                                          JCoServerState oldState, JCoServerState newState) {

        log.info("StateChange on JCoServer: [" + server.getProgramID() + "] "
                + "| State: [" + oldState + "] ==> [" + newState + "]");
    }
}
