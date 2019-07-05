package cn.gitlab.virtualcry.sapjco.config;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Sap connection settings
 *
 * @author VirtualCry
 */
@Data
public class JCoSettings implements Serializable {

    private String settingsName     = UUID.randomUUID().toString();

    /* =============================== Start Connect Config. ============================ */

    // server address
    private String ashost           = "127.0.0.1";

    // system number
    private String sysnr            = "00";

    // system environment
    private String client           = "800";

    // user
    private String user             = "";
    // password
    private String password         = "";

    // language
    private String language         = "ZH";

    // max connections
    private String poolCapacity     = "20";

    // max connection threads
    private String peakLimit        = "10";

    /* ================================ End Connect Config. ============================= */


    /* ================================ Start Server Config. ============================ */

    // gateway host
    private String gatewayHost      = "127.0.0.1";

    // gateway service
    private String gatewayService   = "sapgw00";

    // must same with SM59's programId
    private String programId        = "JAVA_JCO";

    private String connectionCount  = "20";

    /* ================================= End Server Config. ============================= */


    /**
     * Get unique key for server.
     * @return unique key for server.
     */
    public String getServerUniqueKey() {
        return this.gatewayHost + " | " + this.gatewayService + " | " + this.programId;
    }

}
