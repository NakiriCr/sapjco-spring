package cn.gitlab.virtualcry.sapjco.spring.context.annotation;

import cn.gitlab.virtualcry.sapjco.config.JCoDataProvider;
import cn.gitlab.virtualcry.sapjco.spring.context.JCoApplicationContextInitializer.JCoComponentScanRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;

/**
 * EnableJCoRegister
 */
@Import(JCoComponentScanRegistrar.class)
@Slf4j
public class EnableJCoRegister {

    public EnableJCoRegister() {
        if (log.isInfoEnabled())
            log.info("Initializing SAP Java Connector");

        // register provider
        JCoDataProvider.registerInEnvironment();
    }
}
