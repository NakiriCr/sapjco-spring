package org.yanzx.core.extend.sap.jco.factory.semaphore;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 10:19
 */
public class NoUniqueJCoBeanSemaphore extends NoSuchJCoBeanSemaphore {

    private int numberOfBeansFound;

    private Collection<String> beanNamesFound;


    /**
     * Create a new {@code NoUniqueJCoBeanSemaphore}.
     * @param type required type of the non-unique bean
     * @param numberOfBeansFound the number of matching beans
     * @param message detailed message describing the problem
     */
    public NoUniqueJCoBeanSemaphore(Class<?> type, int numberOfBeansFound, String message) {
        super(type, message);
        this.numberOfBeansFound = numberOfBeansFound;
    }

    /**
     * Create a new {@code NoUniqueJCoBeanSemaphore}.
     * @param type required type of the non-unique bean
     * @param beanNamesFound the names of all matching beans (as a Collection)
     */
    public NoUniqueJCoBeanSemaphore(Class<?> type, Collection<String> beanNamesFound) {
        this(type, beanNamesFound.size(), "expected single matching bean but found " + beanNamesFound.size() + ": " +
                StringUtils.collectionToCommaDelimitedString(beanNamesFound));
        this.beanNamesFound = beanNamesFound;
    }

    /**
     * Create a new {@code NoUniqueJCoBeanSemaphore}.
     * @param type required type of the non-unique bean
     * @param beanNamesFound the names of all matching beans (as an array)
     */
    public NoUniqueJCoBeanSemaphore(Class<?> type, String... beanNamesFound) {
        this(type, Arrays.asList(beanNamesFound));
    }


    /**
     * Return the number of beans found when only one matching bean was expected.
     * For a NoUniqueJCoBeanSemaphore, this will usually be higher than 1.
     * @see #getBeanType()
     */
    @Override
    public int getNumberOfBeansFound() {
        return this.numberOfBeansFound;
    }

    /**
     * Return the names of all beans found when only one matching bean was expected.
     * Note that this may be {@code null} if not specified at construction time.
     * @since 4.3
     * @see #getBeanType()
     */
    public Collection<String> getBeanNamesFound() {
        return this.beanNamesFound;
    }
}
