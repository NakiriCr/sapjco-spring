package org.yanzx.core.spring.beans.config.convert.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/1/27 21:55
 */
public class StringToClassConverter implements Converter<String, Class> {

    private static final Log _logger = LogFactory.getLog(StringToClassConverter.class);

    @Override
    public Class convert(String _source) {
        try {
            return Class.forName(_source);
        }catch (ClassNotFoundException _ex) {
            throw new RuntimeException(_ex);
        }
    }
}
