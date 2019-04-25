package org.yanzx.template.sap.server.bootstrap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Description: JCoServer Start Test with Spring.
 *
 * @author VirtualCry
 * @date 2018/12/31 22:33
 */
public class JCoServerBootstrap {

    /**
     * JCoServer Start entrance.
     */
    public static void main(String[] _args) {
        new ClassPathXmlApplicationContext("application.xml");
    }
}
