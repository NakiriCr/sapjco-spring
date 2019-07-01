package cn.gitlab.virtualcry.sapjco.spring.beans.factory.annotation;

import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.Map;
import java.util.Set;

/**
 * {@link org.springframework.beans.factory.support.BeanNameGenerator}
 * implementation for bean classes annotated with the
 * {@link cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent @JCoComponent} annotation
 * or with another annotation that is itself annotated with
 * {@link cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent @JCoComponent} as a
 * meta-annotation. For example, Spring's stereotype annotations (such as
 * {@link cn.gitlab.virtualcry.sapjco.spring.annotation.JCoFunctionHandler @JCoFunctionHandler}) are
 * themselves annotated with
 * {@link cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent @JCoComponent}.
 *
 * <p>If the annotation's value doesn't indicate a bean name, an appropriate
 * name will be built based on the short name of the class (with the first
 * letter lower-cased). For example:
 *
 * @author VirtualCry
 * @see cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent#value()
 * @see cn.gitlab.virtualcry.sapjco.spring.annotation.JCoFunctionHandler#value()
 * @see cn.gitlab.virtualcry.sapjco.spring.annotation.JCoListener#value()
 */
public class JCoAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    private static final String JCO_BEAN_ANNOTATION_CLASSNAME = "cn.gitlab.virtualcry.sapjco.spring.annotation.JCoComponent";

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
