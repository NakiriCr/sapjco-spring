package org.yanzx.core.extend.sap.jco.factory.support;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/30 21:34
 */
public class JCoBeanNameGenerator {

    /**
     * Separator for generated bean names. If a class name or parent name is not
     * unique, "#1", "#2" etc will be appended, until the name becomes unique.
     */
    public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";

    /**
     * Generate a bean name
     * @param _beanFactory _beanFactory
     * @param _beanType _beanType
     * @return bean name.
     */
    public String generateBeanName(JCoBeanFactory _beanFactory, Class _beanType) {

        String _id = _beanType.getName();

        /* Increase counter until the id is unique. */
        int _counter = -1;
        while (_counter == -1 || _beanFactory.isRegistry(_id)) {
            _counter++;
            _id = _beanType.getName() + GENERATED_BEAN_NAME_SEPARATOR + _counter;
        }

        return _id;
    }
}
