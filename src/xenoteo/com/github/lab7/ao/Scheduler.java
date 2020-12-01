package xenoteo.com.github.lab7.ao;

import xenoteo.com.github.lab7.ao.request.MethodRequest;

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
