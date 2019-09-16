package cn.gitlab.virtualcry.sapjco.util.key;

import java.util.UUID;

/**
 * Somethings
 *
 * @author VirtualCry
 */
public class KeyGenerator {

    /**
     * Generate client  key.
     * @return client key.
     */
    public static String generateClientKey() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate server key.
     * @param gatewayHost gateway host
     * @param gatewayService gateway service
     * @param programId program id
     * @return server key.
     */
    public static String generateServerKey(String gatewayHost, String gatewayService, String programId) {
        return gatewayHost + " | " + gatewayService + " | " + programId;
    }

}
