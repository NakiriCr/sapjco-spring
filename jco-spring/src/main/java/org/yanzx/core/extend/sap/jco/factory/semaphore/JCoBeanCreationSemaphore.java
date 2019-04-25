package org.yanzx.core.extend.sap.jco.factory.semaphore;

import org.springframework.beans.FatalBeanException;
import org.springframework.core.NestedRuntimeException;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 *
 * @author VirtualCry
 * @date 2018/12/28 12:50
 */
public class JCoBeanCreationSemaphore extends FatalBeanException {

    private String beanName;

    private String resourceDescription;

    private List<Throwable> relatedCauses;


    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param msg the detail message
     */
    public JCoBeanCreationSemaphore(String msg) {
        super(msg);
    }

    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param msg the detail message
     * @param cause the root cause
     */
    public JCoBeanCreationSemaphore(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param beanName the name of the bean requested
     * @param msg the detail message
     */
    public JCoBeanCreationSemaphore(String beanName, String msg) {
        super("Error creating bean" + (beanName != null ? " with name '" + beanName + "'" : "") + ": " + msg);
        this.beanName = beanName;
    }

    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param beanName the name of the bean requested
     * @param msg the detail message
     * @param cause the root cause
     */
    public JCoBeanCreationSemaphore(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }

    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param resourceDescription description of the resource
     * that the bean definition came from
     * @param beanName the name of the bean requested
     * @param msg the detail message
     */
    public JCoBeanCreationSemaphore(String resourceDescription, String beanName, String msg) {
        super("Error creating bean" + (beanName != null ? " with name '" + beanName + "'" : "") +
                (resourceDescription != null ? " defined in " + resourceDescription : "") + ": " + msg);
        this.resourceDescription = resourceDescription;
        this.beanName = beanName;
    }

    /**
     * Create a new JCoBeanCreationSemaphore.
     * @param resourceDescription description of the resource
     * that the bean definition came from
     * @param beanName the name of the bean requested
     * @param msg the detail message
     * @param cause the root cause
     */
    public JCoBeanCreationSemaphore(String resourceDescription, String beanName, String msg, Throwable cause) {
        this(resourceDescription, beanName, msg);
        initCause(cause);
    }


    /**
     * Return the name of the bean requested, if any.
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Return the description of the resource that the bean
     * definition came from, if any.
     */
    public String getResourceDescription() {
        return this.resourceDescription;
    }

    /**
     * Add a related cause to this bean creation exception,
     * not being a direct cause of the failure but having occurred
     * earlier in the creation of the same bean instance.
     * @param ex the related cause to add
     */
    public void addRelatedCause(Throwable ex) {
        if (this.relatedCauses == null) {
            this.relatedCauses = new LinkedList<Throwable>();
        }
        this.relatedCauses.add(ex);
    }

    /**
     * Return the related causes, if any.
     * @return the array of related causes, or {@code null} if none
     */
    public Throwable[] getRelatedCauses() {
        if (this.relatedCauses == null) {
            return null;
        }
        return this.relatedCauses.toArray(new Throwable[this.relatedCauses.size()]);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.relatedCauses != null) {
            for (Throwable relatedCause : this.relatedCauses) {
                sb.append("\nRelated cause: ");
                sb.append(relatedCause);
            }
        }
        return sb.toString();
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        synchronized (ps) {
            super.printStackTrace(ps);
            if (this.relatedCauses != null) {
                for (Throwable relatedCause : this.relatedCauses) {
                    ps.println("Related cause:");
                    relatedCause.printStackTrace(ps);
                }
            }
        }
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        synchronized (pw) {
            super.printStackTrace(pw);
            if (this.relatedCauses != null) {
                for (Throwable relatedCause : this.relatedCauses) {
                    pw.println("Related cause:");
                    relatedCause.printStackTrace(pw);
                }
            }
        }
    }

    @Override
    public boolean contains(Class<?> exClass) {
        if (super.contains(exClass)) {
            return true;
        }
        if (this.relatedCauses != null) {
            for (Throwable relatedCause : this.relatedCauses) {
                if (relatedCause instanceof NestedRuntimeException &&
                        ((NestedRuntimeException) relatedCause).contains(exClass)) {
                    return true;
                }
            }
        }
        return false;
    }
}
