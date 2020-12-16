package xenoteo.com.github.lab7_8_9.ao.request;

import xenoteo.com.github.lab7_8_9.ao.Buffer;
import xenoteo.com.github.lab7_8_9.ao.future.ConsumerFuture;
import xenoteo.com.github.lab7_8_9.ao.future.Future;

/**
 * Method request for consumers.
 */
public class ConsumerRequest implements MethodRequest{
    private final Buffer buffer;
    private final ConsumerFuture future;
    private final int n;

    public ConsumerRequest(Buffer buffer, int n){
        this.buffer = buffer;
        future = new ConsumerFuture();
        this.n = n;
    }

    @Override
    public boolean guard() {
        return buffer.hasEnoughElements(n);
    }

    @Override
    public void execute() {
        buffer.consume(this);
        future.finish();
    }

    public Future getFuture() {
        return future;
    }

    public int getN() {
        return n;
    }

}
