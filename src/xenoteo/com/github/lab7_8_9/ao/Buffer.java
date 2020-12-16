package xenoteo.com.github.lab7_8_9.ao;

import xenoteo.com.github.lab7_8_9.ao.future.ConsumerFuture;
import xenoteo.com.github.lab7_8_9.ao.request.ConsumerRequest;
import xenoteo.com.github.lab7_8_9.ao.request.ProducerRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * A Servant defines the behavior and state that is being modeled as an Active Object.
 * Servants implement the methods defined in the Proxy and the corresponding Method Requests.
 * A Servant method is invoked when its corresponding Method Request is executed by a Scheduler;
 * thus, Servants execute in the Scheduler’s thread of control.
 *
 * @see xenoteo.com.github.lab7_8_9.ao.request.MethodRequest
 * @see Proxy
 * @see xenoteo.com.github.lab7_8_9.ao.scheduler.Scheduler
 * @see xenoteo.com.github.lab7_8_9.ao.scheduler.ActivationQueue
 */
public class Buffer {
    private final LinkedList<Integer> buffer;
    private final int maxSize;

    public Buffer(int maxSize){
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
    }

    public boolean hasEnoughSpace(List<Integer> newElements){
        return newElements.size() + buffer.size() <= maxSize;
    }

    public boolean hasEnoughElements(int n){
        return buffer.size() >= n;
    }

    public void produce(ProducerRequest request){
        buffer.addAll(request.getElements());
        request.getFuture().finish();
    }

    public void consume(ConsumerRequest request){
        List<Integer> result = new LinkedList<>();
        for (int i = 0; i < request.getN(); i++){
            result.add(buffer.poll());
        }
        ConsumerFuture future = (ConsumerFuture)request.getFuture();
        future.setResult(result);
        future.finish();
    }

}
