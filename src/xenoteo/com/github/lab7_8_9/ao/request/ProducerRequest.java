package xenoteo.com.github.lab7_8_9.ao.request;

import xenoteo.com.github.lab7_8_9.ao.Buffer;
import xenoteo.com.github.lab7_8_9.ao.future.Future;
import xenoteo.com.github.lab7_8_9.ao.future.ProducerFuture;

import java.util.List;

public class ProducerRequest implements MethodRequest{
    private final Buffer buffer;
    private final ProducerFuture future;
    private final List<Integer> elements;

    public ProducerRequest(Buffer buffer, List<Integer> newElements){
        this.buffer = buffer;
        future = new ProducerFuture();
        elements = newElements;
    }

    @Override
    public boolean guard() {
        return buffer.hasEnoughSpace(elements);
    }

    @Override
    public void execute() {
        buffer.produce(this);
        future.finish();
    }

    public Future getFuture() {
        return future;
    }

    public List<Integer> getElements() {
        return elements;
    }
}
