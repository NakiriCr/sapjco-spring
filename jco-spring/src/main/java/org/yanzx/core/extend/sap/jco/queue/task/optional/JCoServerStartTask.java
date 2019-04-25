package org.yanzx.core.extend.sap.jco.queue.task.optional;

import org.springframework.beans.BeanUtils;
import org.yanzx.core.extend.sap.jco.beans.JCoServerConfig;
import org.yanzx.core.extend.sap.jco.client.proxy.JCoClientProxy;
import org.yanzx.core.extend.sap.jco.client.proxy.trait.JCoClient;
import org.yanzx.core.extend.sap.jco.queue.task.JCoContextInitializeTask;
import org.yanzx.core.extend.sap.jco.server.proxy.JCoServerProxy;
import org.yanzx.core.extend.sap.jco.server.proxy.trait.JCoServer;
import org.yanzx.core.extend.sap.jco.stereotype.JCoInitializeTask;

import javax.annotation.Resource;

/**
 * Description: 通过注册初始化任务进行启动
 *
 * @author VirtualCry
 * @date 2018/12/30 1:41
 */
@JCoInitializeTask
public class JCoServerStartTask implements JCoContextInitializeTask {

    private static final String JCO_SERVER_START_TASK_NAME = "Task - JCoServer Start. [Optional]";

    @Resource
    private JCoServerConfig _config;

    @Override
    public String getName() {
        return JCO_SERVER_START_TASK_NAME;
    }

    /**
     * JCoServer Start.
     */
    @Override
    public void run() {
        JCoServer _server = new JCoServerProxy(_config);
        _server.initAndStart();

        JCoServerConfig _config = new JCoServerConfig();
        BeanUtils.copyProperties(this._config, _config);
        _config.setDestinationName(_config.getDestinationName() + "#1");
        JCoClient _client = new JCoClientProxy(_config);
        _client.init();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
