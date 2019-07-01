package cn.gitlab.virtualcry.sapjco.client.semaphore;

/**
 * Client created on error semaphore
 *
 * @author VirtualCry
 */
public class JCoClientCreatedOnErrorSemaphore extends RuntimeException {

    /**
     * Create a new {@code JCoClientCreatedOnErrorSemaphore}.
     * @param message message
     */
    public JCoClientCreatedOnErrorSemaphore(String message) {
        super(message);
    }


    /**
     * Create a new {@code JCoClientCreatedOnErrorSemaphore}.
     * @param message message
     * @param cause cause
     */
    public JCoClientCreatedOnErrorSemaphore(String message, Throwable cause) {
        super(message, cause);
    }

}
