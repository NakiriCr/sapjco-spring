package org.yanzx.template.sap.server.bootstrap.config.util;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static org.springframework.util.ReflectionUtils.*;

/**
 * Property utils
 */
public class PropertyUtils {

    /**
     * Autowired property value to bean.
     * @param environment spring environment
     * @param prefix property prefix
     * @param propertyBean bean
     */
    public static void autowirePropertyBean(Environment environment, String prefix, Object propertyBean) {
        Assert.notNull(prefix, "Property config could not be null.");
        final String prefixStr = StringUtils.hasText(prefix) ? prefix + "." : "";
        FieldCallback callback = field -> {
            makeAccessible(field);
            Object propertyValue = environment.getProperty(prefixStr + field.getName(), field.getType());
            if (propertyValue == null) {
                propertyValue = environment.getProperty(prefixStr + parseLowerCamelToLowerHyphen(field.getName()), field.getType());
            }

            if (propertyValue != null) {
                setField(field, propertyBean, propertyValue);
            }
        };
        doWithFields(propertyBean.getClass(), callback);
    }

    private static String parseLowerCamelToLowerHyphen(String _value) {
        if (StringUtils.isEmpty(_value)) return _value;
        StringBuilder _strBuilder = new StringBuilder();
        char[] _chars = _value.toCharArray();
        for (int _index = 0; _index <_chars.length; _index++) {
            _strBuilder.append(Character.isUpperCase(_chars[_index]) && _index > 0 ?
                    "-" + Character.toLowerCase(_chars[_index]) : Character.toLowerCase(_chars[_index]));
        }
        return _strBuilder.toString();
    }

}
