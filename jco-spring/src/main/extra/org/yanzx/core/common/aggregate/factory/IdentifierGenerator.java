package org.yanzx.core.common.aggregate.factory;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description: ID generator.
 *
 * @author VirtualCry
 * @date 2018/5/3 22:32
 */
public class IdentifierGenerator {

    public static Serializable build() {
        return getUUID();
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    private static Serializable getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
