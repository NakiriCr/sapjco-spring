package org.yanzx.template.sap.server.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.yanzx.core.extend.sap.jco.beans.JCoSettings;

import static org.yanzx.template.sap.server.bootstrap.config.util.PropertyUtils.autowirePropertyBean;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/31 22:25
 */
@Configuration
@PropertySource(value = "classpath:sapjco.properties")
public class JCoConfiguration {

    @Bean
    public JCoSettings jCoSettings(Environment environment) {
        JCoSettings settings = new JCoSettings();
        autowirePropertyBean(environment, "jco-server", settings);
        return settings;
    }
}
