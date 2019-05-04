package org.yanzx.template.sap.server.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yanzx.core.extend.sap.spring.context.annotation.JCoComponentScan;

/**
 * Start with SpringBoot
 *
 * @author VirtualCry
 * @date 2018/12/31 22:33
 */
@JCoComponentScan("org.yanzx.template.sap")
@SpringBootApplication
public class JCoServerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(JCoServerBootstrap.class, args);
    }
}
