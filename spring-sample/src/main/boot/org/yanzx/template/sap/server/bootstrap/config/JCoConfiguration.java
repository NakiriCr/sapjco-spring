package org.yanzx.template.sap.server.bootstrap.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yanzx.core.common.security.cipher.AESCert;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;

import javax.annotation.Resource;

import static org.yanzx.core.spring.beans.config.PropertyUtils.autowirePropertyBean;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/31 22:25
 */
@Configuration
@PropertySource(value = "classpath:sapjco.properties")
public class JCoConfiguration {

    @Component
    static class SpringJCoServerConfig extends JCoServerConfig implements InitializingBean {
        @Resource
        private Environment _env;
        @Override
        public void afterPropertiesSet() throws Exception {
            autowirePropertyBean(_env, "jco-server", this);    /* 自动装配属性. */
            if ("".equals(getPassword()))
                setPassword(AESCert.decrypt(getCipher(), getCipherVector())); /* if password isn't set, use cipher to decrypt.  */
        }
    }
}
