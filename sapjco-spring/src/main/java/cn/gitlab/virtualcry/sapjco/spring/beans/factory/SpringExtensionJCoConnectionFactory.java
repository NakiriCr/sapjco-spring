package cn.gitlab.virtualcry.sapjco.spring.beans.factory;

import cn.gitlab.virtualcry.sapjco.beans.factory.DefaultJCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactory;
import cn.gitlab.virtualcry.sapjco.beans.factory.JCoConnectionFactoryProvider;
import org.springframework.beans.factory.InitializingBean;

/**
 * Extension for {@link JCoConnectionFactory}.
 * Instances will be automatically registered to {@link JCoConnectionFactoryProvider}.
 *
 * @author VirtualCry
 */
public class SpringExtensionJCoConnectionFactory
        extends DefaultJCoConnectionFactory implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        JCoConnectionFactoryProvider.getSingleton().register(this); // register in provider.
    }
}
