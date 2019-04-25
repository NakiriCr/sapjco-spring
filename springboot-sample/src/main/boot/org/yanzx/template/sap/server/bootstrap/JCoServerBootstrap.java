package org.yanzx.template.sap.server.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yanzx.core.extend.sap.jco.queue.task.optional.JCoServerStartTask;
import org.yanzx.core.extend.sap.spring.annotation.JCoBeanScan;
import org.yanzx.core.extend.sap.spring.registrar.annotation.EnableJCoFramework;
import org.yanzx.core.registrar.annotation.EnableVirtualCFramework;

/**
 * Description: JCoServer Start Test with Spring.
 *
 * @author VirtualCry
 * @date 2018/12/31 22:33
 */
@JCoBeanScan(basePackages = {
        "org.yanzx.template.sap",
})
@JCoBeanScan(basePackageClasses = {
        JCoServerStartTask.class
})
@EnableJCoFramework
@EnableVirtualCFramework
@SpringBootApplication
public class JCoServerBootstrap {

    public static void main(String[] _args) {
        SpringApplication.run(JCoServerBootstrap.class, _args);
    }
}
