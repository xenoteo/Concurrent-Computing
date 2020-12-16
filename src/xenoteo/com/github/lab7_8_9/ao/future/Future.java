package xenoteo.com.github.lab7_8_9.ao.future;

/**
 * A Future allows a client to obtain the results of method invocations after the Servant (Buffer)
 * finishes executing the method. When a client invokes methods through a Proxy,
 * a Future is returned immediately to the client. The Future reserves space for the invoked method to store its results.
 * When a client wants to obtain these results, it can “rendezvous” with the Future, either blocking or
 * polling until the results are computed and stored into the Future.
 *
 * @see xenoteo.com.github.lab6.Buffer
 * @see xenoteo.com.github.lab3.Producer
 * @see xenoteo.com.github.lab3.Consumer
 */
public abstract class Future {
    protected boolean finished = false;

    public void finish(){
        finished = true;
    }

    public boolean isFinished(){
        return finished;
    }
}
