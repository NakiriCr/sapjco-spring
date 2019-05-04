package org.yanzx.template.sap.server.bootstrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Start with Spring
 *
 * @author VirtualCry
 */
public class JCoServerBootstrap {

    public static void main(String[] args) {
        new JCoServerBootstrap().run();
    }

    /**
     * Run the Spring application, creating and refreshing a new
     * {@link ApplicationContext}.
     * @return a running {@link ApplicationContext}
     */
    public ConfigurableApplicationContext run() {

        // create ctx
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext();

        // initialize ctx !!! very import.
        applyInitializers(ctx);

        // set config
        ctx.setConfigLocation("application.xml");

        // refresh
        ctx.refresh();

        return ctx;
    }


    /* =============================================================================== */

    /**
     * Apply any {@link ApplicationContextInitializer}s to the context before it is
     * refreshed.
     * @param ctx the configured ApplicationContext (not refreshed yet)
     * @see ConfigurableApplicationContext#refresh()
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void applyInitializers(ConfigurableApplicationContext ctx) {
        for (ApplicationContextInitializer initializer : getInitializers(ctx)) {
            Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(
                    initializer.getClass(), ApplicationContextInitializer.class);
            Assert.isInstanceOf(requiredType, ctx, "Unable to call initializer.");
            initializer.initialize(ctx);
        }
    }

    /**
     * Returns read-only ordered Set of the {@link ApplicationContextInitializer}s that
     * will be applied to the Spring {@link ApplicationContext}.
     * @return the initializers
     */
    private Set<ApplicationContextInitializer> getInitializers(ConfigurableApplicationContext ctx) {
        List<ApplicationContextInitializer> initializers =
                SpringFactoriesLoader.loadFactories(ApplicationContextInitializer.class, ctx.getClassLoader());
        return asUnmodifiableOrderedSet(initializers);
    }


    private static <E> Set<E> asUnmodifiableOrderedSet(Collection<E> elements) {
        List<E> list = new ArrayList<E>(elements);
        list.sort(AnnotationAwareOrderComparator.INSTANCE);
        return new LinkedHashSet<E>(list);
    }
}
