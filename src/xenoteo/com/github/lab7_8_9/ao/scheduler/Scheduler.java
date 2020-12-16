package xenoteo.com.github.lab7_8_9.ao.scheduler;

import xenoteo.com.github.lab7_8_9.ao.request.MethodRequest;

/**
 * A Scheduler runs in a different thread than its clients, managing an Activation Queue
 * of Method Requests that are pending execution.
 *
 * @see MethodRequest
 * @see ActivationQueue
 */
public class Scheduler {
    private final ActivationQueue queue;

    public Scheduler() {
        queue = new ActivationQueue();
    }

    public void enqueue(MethodRequest request){
        queue.enqueue(request);
    }

    public void dispatch(){
        MethodRequest request = queue.dequeue();
        if (request != null) request.execute();
    }
}
