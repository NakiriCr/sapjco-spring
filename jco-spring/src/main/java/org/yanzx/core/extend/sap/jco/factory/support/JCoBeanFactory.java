package org.yanzx.core.extend.sap.jco.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yanzx.core.common.proxy.util.AopTargetUtils;
import org.yanzx.core.extend.sap.jco.stereotype.JCoBean;
import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanCreationSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.JCoBeanNotOfRequiredTypeSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.NoSuchJCoBeanSemaphore;
import org.yanzx.core.extend.sap.jco.factory.semaphore.NoUniqueJCoBeanSemaphore;
import org.yanzx.core.spring.util.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 8:53
 */
@SuppressWarnings("unchecked")
public class JCoBeanFactory {

    /* Log. */
    protected static final Log _logger = LogFactory.getLog(JCoBeanFactory.class);

    /* ============================================================================================================= */

    /* JCoBean Cache. */
    private final Map<String, Object> _beanCacheHolder = new ConcurrentHashMap<>();

    /* _beanNameGenerator */
    private final JCoBeanNameGenerator _beanNameGenerator = new JCoBeanNameGenerator();

    /**
     * Registry.
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public <T> void registry(T _bean) throws JCoBeanCreationSemaphore {

        /* Ger @JCoBean */
        JCoBean _annotation = AnnotationUtils.findMergedAnnotation(_bean.getClass(), JCoBean.class);

        if (_annotation == null) throw new JCoBeanCreationSemaphore("Could not find @JCoBean in class: [" + _bean.getClass().getName() + "]");

        /* Get beanName. */
        String _beanName = ("".equals(_annotation.value())) ? _beanNameGenerator.generateBeanName(this, _bean.getClass()) : _annotation.value();

        /* Registry. */
        registry(_beanName, _bean);
    }

    /**
     * Registry.
     * @param _beanName _beanName
     * @param _bean _bean
     * @throws JCoBeanCreationSemaphore JCoBeanCreationSemaphore
     */
    public <T> void registry(String _beanName, T _bean) throws JCoBeanCreationSemaphore {

        if (isRegistry(_beanName))
            throw new JCoBeanCreationSemaphore("JCoBean: [" + _beanName + "] has already registry.");

        /* Logout. */
        if (_logger.isDebugEnabled())
            _logger.debug("==> Creating " + AopTargetUtils.getTarget(_bean).getClass().getSimpleName() + " with name '" + _beanName + "'");

        /* Put. */
        _beanCacheHolder.put(_beanName, _bean);
    }

    /* ============================================================================================================= */

    /**
     * Check is already registry in factory
     * @param _beanName _beanName
     * @return boolean
     */
    public boolean isRegistry(String _beanName) {
        if (_beanName == null) throw new IllegalArgumentException("Bean name must not be null");
        return _beanCacheHolder.containsKey(_beanName);
    }

    /* ============================================================================================================= */

    /**
     * Get bean.
     * @param _beanName _beanName
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     * @return Object
     */
    public Object getBean(String _beanName) throws NoSuchJCoBeanSemaphore {
        Object _bean = _beanCacheHolder.get(_beanName);
        if (_bean == null) throw new NoSuchJCoBeanSemaphore(_beanName);
        return _bean;
    }

    /**
     * Get bean.
     * @param _beanName _beanName
     * @param _beanType _beanType
     * @return T
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     * @throws JCoBeanNotOfRequiredTypeSemaphore JCoBeanNotOfRequiredTypeSemaphore
     */
    public <T> T getBean(String _beanName, Class<T> _beanType) throws NoSuchJCoBeanSemaphore, JCoBeanNotOfRequiredTypeSemaphore {
        Object _bean = getBean(_beanName);
        if (!_beanType.isInstance(_bean)) throw new JCoBeanNotOfRequiredTypeSemaphore(_beanName, _beanType, _bean.getClass());
        return (T) _bean;
    }

    /**
     * Get bean.
     * @param _beanType _beanType
     * @return T
     * @throws NoUniqueJCoBeanSemaphore NoUniqueJCoBeanSemaphore
     * @throws NoSuchJCoBeanSemaphore NoSuchJCoBeanSemaphore
     *
     */
    public <T> T getBean(Class<T> _beanType) throws NoUniqueJCoBeanSemaphore, NoSuchJCoBeanSemaphore {
        String[] _beanNames = getBeanNamesForType(_beanType);
        if (_beanNames.length == 1) {
            return getBean(_beanNames[0], _beanType);
        }
        else if (_beanNames.length > 1) {
            throw new NoUniqueJCoBeanSemaphore(_beanType, _beanNames);
        }
        else {
            throw new NoSuchJCoBeanSemaphore(_beanType);
        }
    }

    /**
     * Get beans.
     * @param _beanType _beanType
     * @return List<T>
     */
    public <T> List<T> getBeans(Class<T> _beanType) {
        List<T> _beans = new ArrayList<>();
        String[] _beanNames = getBeanNamesForType(_beanType);
        for (String _beanName : _beanNames) {
            _beans.add(getBean(_beanName, _beanType));
        }
        return _beans;
    }

    /**
     * Get beanName.
     * @param _beanType _beanType
     * @return String[]
     */
    public String[] getBeanNamesForType(Class _beanType) {
        List<String> matches = new ArrayList<>();
        for (Map.Entry<String, Object> _entry : _beanCacheHolder.entrySet()) {
            if (_beanType == null || _beanType.isInstance(_entry.getValue())) {
                matches.add(_entry.getKey());
            }
        }
        return matches.toArray(new String[]{});
    }

    /**
     * Un registry bean.
     * @param _beanName _beanName
     */
    public void unRegistry(String _beanName) {
        _beanCacheHolder.remove(_beanName);
    }
}
