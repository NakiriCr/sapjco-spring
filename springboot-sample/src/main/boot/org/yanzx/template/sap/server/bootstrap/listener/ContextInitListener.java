package org.yanzx.template.sap.server.bootstrap.listener;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;
import org.yanzx.core.extend.sap.jco.server.wrapper.JCoServerWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ContextInitListener
 */
@Component
public class ContextInitListener implements ApplicationContextAware, ApplicationRunner {

    // thread safe init flag
    private volatile AtomicBoolean isInit = new AtomicBoolean(false);

    private ApplicationContext ctx;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (!isInit.compareAndSet(false, true))
            return;

        JCoServerWrapper server = new JCoServerWrapper(
                ctx.getBean(JCoSettings.class));
        server.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
