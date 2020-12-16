package xenoteo.com.github.lab7_8_9.ao;

import xenoteo.com.github.lab7_8_9.ao.future.Future;
import xenoteo.com.github.lab7_8_9.ao.request.ConsumerRequest;
import xenoteo.com.github.lab7_8_9.ao.request.MethodRequest;
import xenoteo.com.github.lab7_8_9.ao.request.ProducerRequest;
import xenoteo.com.github.lab7_8_9.ao.scheduler.Scheduler;

import java.util.List;

/**
 * A Proxy provides an interface that allows clients to invoke publically accessible methods on an Active Object.
 * When a client invokes a method defined by the Proxy, this triggers the construction and queueing of
 * a Method Request object on to the Scheduler’s Activation Queue, all of which occurs in the client’s thread of control.
 *
 * @see MethodRequest
 * @see Scheduler
 */
public class Proxy {
    private final Scheduler scheduler;
    private final Buffer buffer;

    public Proxy(Buffer buffer) {
        scheduler = new Scheduler();
        this.buffer = buffer;
    }

    public Future produce(List<Integer> newElements){
        MethodRequest request = new ProducerRequest(buffer, newElements);
        scheduler.enqueue(request);
        return request.getFuture();
    }

    public Future consume(int n){
        MethodRequest request = new ConsumerRequest(buffer, n);
        scheduler.enqueue(request);
        return request.getFuture();
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
