package org.yanzx.template.sap.server.bootstrap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/31 22:25
 */
@Configuration
public class JCoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jco-server")
    public JCoSettings jCoSettings() {
        return new JCoSettings();
    }
}
