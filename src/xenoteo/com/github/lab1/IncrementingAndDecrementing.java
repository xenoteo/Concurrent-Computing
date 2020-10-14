package xenoteo.com.github.lab1;

/**
 * Task incrementing and decrementing a counter n times,
 * where n is a number provided in the constructor.
 * Without data protection.
 */
public class IncrementingAndDecrementing implements Runnable{
    private final Counter counter;
    /**
     * Number of operations to perform on counter
     */
    private final int n;

    public IncrementingAndDecrementing(Counter counter, int n) {
        this.counter = counter;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            counter.increment();
            counter.decrement();
        }
    }
}
