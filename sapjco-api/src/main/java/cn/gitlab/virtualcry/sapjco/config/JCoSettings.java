package cn.gitlab.virtualcry.sapjco.config;

import cn.gitlab.virtualcry.sapjco.util.key.KeyGenerator;
import lombok.*;

import java.io.Serializable;

/**
 * Sap connection settings
 *
 * @author VirtualCry
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JCoSettings implements Serializable {

    /* =============================== Start Connect Config. ============================ */

    @Builder.Default private String ashost      = "127.0.0.1";  // server address
    @Builder.Default private String sysnr       = "00";         // system number
    @Builder.Default private String client      = "800";        // system environment
    @Builder.Default private String user        = "";           // user
    @Builder.Default private String password    = "";           // password
    @Builder.Default private String language    = "ZH";         // language
    @Builder.Default private String poolCapacity = "20";        // max connections
    @Builder.Default private String peakLimit    = "10";        // max connection threads

    /* ================================ End Connect Config. ============================= */


    /* ================================ Start Server Config. ============================ */

    @Builder.Default private String gatewayHost = "127.0.0.1";      // gateway host
    @Builder.Default private String programId   = "JAVA_JCO";       // must same with SM59's programId
    @Builder.Default private String gatewayService  = "sapgw00";    // gateway service
    @Builder.Default private String connectionCount = "20";

    /* ================================= End Server Config. ============================= */

    @Getter(AccessLevel.PRIVATE)
    private final String defaultKey = KeyGenerator.generateClientKey();

    /**
     * Get unique key.
     * @param type type
     * @return key.
     */
    public String getUniqueKey(Connections type) {
        switch (type) {
            case CLIENT:
                return defaultKey;
            case SERVER:
                return KeyGenerator.generateServerKey(this.gatewayHost, this.gatewayService, this.programId);
                default:
                    return null;
        }
    }
}
