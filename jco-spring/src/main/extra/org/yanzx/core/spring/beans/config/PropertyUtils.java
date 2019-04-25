package org.yanzx.core.spring.beans.config;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import static org.springframework.util.ReflectionUtils.*;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/11 22:59
 */
public class PropertyUtils {

    /**
     * Autowired property value to bean.
     * @param _env spring environment
     * @param _prefix property prefix
     * @param _propertyBean bean
     */
    public static void autowirePropertyBean(Environment _env, String _prefix, Object _propertyBean) throws InvocationTargetException, IllegalAccessException {
        Assert.notNull(_prefix, "Property config could not be null.");
        doWithFields(_propertyBean.getClass(), _field -> {
            makeAccessible(_field);
            Object _propertyValue = _env.getProperty(_prefix + "." + _field.getName(), _field.getType());
            if (_propertyValue == null) {
                _propertyValue = _env.getProperty(_prefix + "." + parseLowerCamelToLowerHyphen(_field.getName()), _field.getType());
            }

            if (_propertyValue != null) {
                setField(_field, _propertyBean, _propertyValue);
            }
        });
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
