package org.yanzx.core.extend.sap.spring.factory.support;

import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2019/2/11 15:24
 */
public class JCoAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    private static final String JCO_BEAN_ANNOTATION_CLASSNAME = "org.yanzx.core.extend.sap.jco.stereotype.JCoBean";

    /**
     * Check whether the given annotation is a stereotype that is allowed
     * to suggest a component name through its annotation {@code value()}.
     * @param annotationType the name of the annotation class to check
     * @param metaAnnotationTypes the names of meta-annotations on the given annotation
     * @param attributes the map of attributes for the given annotation
     * @return whether the annotation qualifies as a stereotype with component name
     */
    protected boolean isStereotypeWithNameValue(String annotationType,
                                                Set<String> metaAnnotationTypes, Map<String, Object> attributes) {

        boolean isStereotype = annotationType.equals(JCO_BEAN_ANNOTATION_CLASSNAME) ||
                metaAnnotationTypes.contains(JCO_BEAN_ANNOTATION_CLASSNAME) ||
                annotationType.equals("javax.annotation.ManagedBean") ||
                annotationType.equals("javax.inject.Named");

        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }
}
