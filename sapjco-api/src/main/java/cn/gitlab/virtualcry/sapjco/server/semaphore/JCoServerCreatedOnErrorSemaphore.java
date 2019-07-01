package cn.gitlab.virtualcry.sapjco.server.semaphore;

/**
 * Server created on error semaphore
 *
 * @author VirtualCry
 */
public class JCoServerCreatedOnErrorSemaphore extends RuntimeException {

    /**
     * Create a new {@code JCoServerCreatedOnErrorSemaphore}.
     * @param message message
     */
    public JCoServerCreatedOnErrorSemaphore(String message) {
        super(message);
    }


    /**
     * Create a new {@code JCoServerCreatedOnErrorSemaphore}.
     * @param message message
     * @param cause cause
     */
    public JCoServerCreatedOnErrorSemaphore(String message, Throwable cause) {
        super(message, cause);
    }

}
