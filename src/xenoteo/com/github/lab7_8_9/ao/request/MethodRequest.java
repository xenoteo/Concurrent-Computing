package xenoteo.com.github.lab7_8_9.ao.request;

import xenoteo.com.github.lab7_8_9.ao.future.Future;

public interface MethodRequest {

    boolean guard();

    void execute();

    Future getFuture();
}
