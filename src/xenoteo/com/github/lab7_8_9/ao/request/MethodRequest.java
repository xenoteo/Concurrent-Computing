package xenoteo.com.github.lab7_8_9.ao.request;

import xenoteo.com.github.lab7_8_9.ao.future.Future;

/**
 * A Method Request is used to pass context information about a specific method invocation on a Proxy,
 * such as method parameters and code, from the Proxy to a Scheduler running in a separate thread. The interface
 * contains guard methods that can be used to determine when a Method Request’s synchronization constraints are met.
 * Instances of these classes are created by the proxy when its methods are invoked and contain the specific context
 * information necessary to execute these method invocations and return any results back to clients.
 *
 * @see xenoteo.com.github.lab7_8_9.ao.Proxy
 * @see xenoteo.com.github.lab7_8_9.ao.scheduler.Scheduler
 * @see xenoteo.com.github.lab7_8_9.ao.scheduler.ActivationQueue
 */
public interface MethodRequest {

    boolean guard();

    void execute();

    Future getFuture();
}
