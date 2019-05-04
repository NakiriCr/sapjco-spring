package org.yanzx.core.extend.sap.jco.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Sap connection settings
 *
 * @author VirtualCry
 */
@Data
public class JCoSettings implements Serializable {

    private String settingsName;

    /* =============================== Start Connect Config. ============================ */

    // 服务器地址
    private String ashost           = "127.0.0.1";

    // 系统编号
    private String sysnr            = "00";

    // SAP集团
    private String client           = "800";

    // 用户名
    private String user             = "";
    // 密码
    private String password         = "";

    // 语音
    private String language         = "ZH";

    // 最大连接数
    private String poolCapacity     = "20";

    // 最大连接线程
    private String peakLimit        = "10";

    /* ================================ End Connect Config. ============================= */


    /* ================================ Start Server Config. ============================ */

    // 网关地址
    private String gatewayHost      = "127.0.0.1";

    // TCP服务sapgw是固定的，后面的00就是SAP实例系统编号，也可直接是端口号（端口号可以在etc/server文件中找sapgw00所对应的端口号）
    private String gatewayService   = "sapgw00";

    // 这里的程序ID来自于SM59中设置的Program ID，必须相同
    private String programId        = "JAVA_JCO";

    private String connectionCount  = "20";

    /* ================================= End Server Config. ============================= */
}
