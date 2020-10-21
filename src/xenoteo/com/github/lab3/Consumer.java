package xenoteo.com.github.lab3;

/**
 * Simple consumer implementation.
 */
public class Consumer implements Runnable{
    private Factory factory;
    private int id;

    public Consumer(int id, Factory factory) {
        this.factory = factory;
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            int val = factory.consume(id);
        }
    }
}
