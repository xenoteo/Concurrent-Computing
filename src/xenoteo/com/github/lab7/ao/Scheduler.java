package xenoteo.com.github.lab7.ao;

import xenoteo.com.github.lab7.ao.request.MethodRequest;

public class Scheduler {
    private ActivationQueue queue;

    public Scheduler() {
        queue = new ActivationQueue();
    }

    public void enqueue(MethodRequest request){

    }

    public void dispatch(){
        queue.dequeue().execute();
    }
}
