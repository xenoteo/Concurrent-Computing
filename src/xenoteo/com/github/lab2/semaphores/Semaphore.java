package xenoteo.com.github.lab2.semaphores;

/**
 * Simple binary semaphore implementation.
 */
public class Semaphore {
    private boolean busy = false;

    public synchronized void acquire() throws InterruptedException {
        while (busy) wait();
        busy = true;
    }

    public synchronized void release() {
        busy = false;
        notify();
    }
}