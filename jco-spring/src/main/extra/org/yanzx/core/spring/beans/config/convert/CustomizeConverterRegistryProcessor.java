package org.yanzx.core.spring.beans.config.convert;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.yanzx.core.spring.beans.config.convert.converter.StringToClassConverter;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/1/27 22:20
 */
@Component
public class CustomizeConverterRegistryProcessor implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

    private Environment _env;
    @Override
    public void setEnvironment(Environment _env) {
        this._env = _env;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory _beanFactory) throws BeansException {
        ConfigurableConversionService _conversionService = ((AbstractEnvironment)_env).getConversionService();
        _conversionService.addConverter(new StringToClassConverter());
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
