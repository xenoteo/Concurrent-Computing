package xenoteo.com.github.lab7.ao.request;

import xenoteo.com.github.lab7.ao.future.Future;

public interface MethodRequest {

    boolean guard();

    void execute();

    Future getFuture();
}
