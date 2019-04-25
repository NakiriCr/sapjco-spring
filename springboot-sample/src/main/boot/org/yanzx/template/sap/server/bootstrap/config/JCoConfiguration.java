package org.yanzx.template.sap.server.bootstrap.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.yanzx.core.common.security.cipher.AESCert;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/31 22:25
 */
@Configuration
public class JCoConfiguration {

    @Component
    @ConfigurationProperties(prefix = "jco-server")
    public static class SpringJCoServerConfig extends JCoServerConfig implements InitializingBean {
        @Override
        public void afterPropertiesSet() {
            if ("".equals(getPassword()))
                setPassword(AESCert.decrypt(getCipher(), getCipherVector())); /* if password isn't set, use cipher to decrypt.  */
        }
    }
}
