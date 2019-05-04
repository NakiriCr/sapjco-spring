package org.yanzx.template.sap.server.bootstrap.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;
import org.yanzx.core.extend.sap.jco.server.wrapper.JCoServerWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ContextInitListener
 */
@Component
public class ContextInitListener implements ApplicationListener<ContextRefreshedEvent> {

    // thread safe init flag
    private volatile AtomicBoolean isInit = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (!isInit.compareAndSet(false, true))
            return;

        JCoServerWrapper server = new JCoServerWrapper(
                event.getApplicationContext().getBean(JCoSettings.class));
        server.start();

    }
}
