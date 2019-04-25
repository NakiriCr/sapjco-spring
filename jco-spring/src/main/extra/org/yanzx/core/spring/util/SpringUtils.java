package org.yanzx.core.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: Spring utils.
 *
 * @author VirtualCry
 * @date 2019/2/24 15:16
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext _context;

    @Override
    public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
        _context = _applicationContext;
    }

    /**
     * Get applicationContext instance.
     */
    public static ApplicationContext getApplicationContext(){
        return _context;
    }

    /**
     * Get bean by bean's name.
     * @param _beanName _beanName
     */
    public static Object getBean(String _beanName){
        return _context.getBean(_beanName);
    }

    /**
     * Get bean by bean's type.
     * @param _beanType _beanType
     */
    public static <T> T getBean(Class<T> _beanType){
        return _context.getBean(_beanType);
    }

    /**
     * Get bean by bean's name and type.
     * @param _beanName _beanName
     * @param _beanType _beanType
     */
    public static <T> T getBean(String _beanName, Class<T> _beanType){
        return _context.getBean(_beanName, _beanType);
    }

    /**
     * Get beans by bean's type.
     * @param _beanType _beanType
     */
    public static <T> List<T> getBeans(Class<T> _beanType){
        return Stream.of(_context.getBeanNamesForType(_beanType))
                .map(_candidateName -> _context.getBean(_candidateName, _beanType))
                .collect(Collectors.toList());
    }
}
