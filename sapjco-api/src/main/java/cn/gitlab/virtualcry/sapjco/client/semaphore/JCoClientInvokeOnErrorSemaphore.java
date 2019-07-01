package cn.gitlab.virtualcry.sapjco.client.semaphore;

/**
 * Client invoke on error semaphore
 *
 * @author VirtualCry
 */
public class JCoClientInvokeOnErrorSemaphore extends RuntimeException {

    /**
     * Create a new {@code JCoClientInvokeOnErrorSemaphore}.
     * @param message message
     */
    public JCoClientInvokeOnErrorSemaphore(String message) {
        super(message);
    }


    /**
     * Create a new {@code JCoClientInvokeOnErrorSemaphore}.
     * @param message message
     * @param cause cause
     */
    public JCoClientInvokeOnErrorSemaphore(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Create a new {@code JCoClientInvokeOnErrorSemaphore}.
     * @param cause cause
     */
    public JCoClientInvokeOnErrorSemaphore(Throwable cause) {
        super(cause);
    }

}
