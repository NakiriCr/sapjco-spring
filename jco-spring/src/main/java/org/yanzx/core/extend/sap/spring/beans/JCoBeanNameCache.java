package org.yanzx.core.extend.sap.spring.beans;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/11 0:06
 */
public class JCoBeanNameCache {

    private final static Set<String> _beanNames = new ConcurrentSkipListSet<>();

    /**
     * Cache JCoBean name.
     * @param _beanName _beanName
     */
    public static void cache(String _beanName) {
        _beanNames.add(_beanName);
    }

    /**
     * Get all caches.
     * @return  String[]
     */
    public static String[] getCaches() {
        return _beanNames.toArray(new String[]{});
    }
}
