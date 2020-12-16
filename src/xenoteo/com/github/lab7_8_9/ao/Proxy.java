package xenoteo.com.github.lab7_8_9.ao;

import xenoteo.com.github.lab7_8_9.ao.future.Future;
import xenoteo.com.github.lab7_8_9.ao.request.ConsumerRequest;
import xenoteo.com.github.lab7_8_9.ao.request.MethodRequest;
import xenoteo.com.github.lab7_8_9.ao.request.ProducerRequest;
import xenoteo.com.github.lab7_8_9.ao.scheduler.Scheduler;

import java.util.List;

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
