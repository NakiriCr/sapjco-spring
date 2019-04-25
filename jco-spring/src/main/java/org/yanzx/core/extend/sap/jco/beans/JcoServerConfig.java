package org.yanzx.core.extend.sap.jco.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/22 11:47
 */
@Data
public class JCoServerConfig implements Serializable {

    public static final String DEFAULT_JCO_LISTENER_BASE_PACKAGES = "org.yanzx.core.extend.sap.jco.server.listener";

    public static final String DEFAULT_CONTEXT_INIT_BASE_PACKAGES = "org.yanzx.core.extend.sap.spring.context";

    private static final String EMPTY_STR = "";

    /* ================================================================================== */

    private String serverName;

    private String destinationName;

    /* =============================== Start Connect Config. ============================ */

    /* 服务器地址 */
    private String ashost = "127.0.0.1";

    /* 系统编号 */
    private String sysnr = "00";

    /* SAP集团 */
    private String client = "800";

    /* 用户名 */
    private String user = EMPTY_STR;
    /* 明文密码 */
    private String password = EMPTY_STR;

    /* 暗文 */
    private String cipher = EMPTY_STR;
    /* 公钥 */
    private String cipherVector = EMPTY_STR;

    /* 语音 */
    private String language = "EN";

    /* 最大连接数  */
    private String poolCapacity = "20";

    /* 最大连接线程 */
    private String peakLimit = "10";

    /* ================================ End Connect Config. ============================= */


    /* ================================ Start Server Config. ============================ */

    /* 网关地址 */
    private String gatewayHost = "127.0.0.1";

    /* TCP服务sapgw是固定的，后面的00就是SAP实例系统编号，也可直接是端口号（端口号可以在etc/server文件中找sapgw00所对应的端口号） */
    private String gatewayService = "sapgw00";

    /* 这里的程序ID来自于SM59中设置的Program ID，必须相同 */
    private String programId = "JAVA_JCO";

    private String connectionCount = "20";

    /* ================================= End Server Config. ============================= */
}
