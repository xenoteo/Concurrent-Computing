package xenoteo.com.github.lab5.booleanVariables;

import java.util.List;

/**
 * Simple consumer implementation.
 */
public class Consumer implements Runnable{
    private Factory factory;
    private int id;
    private int maxSize;

    public Consumer(int id, Factory factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            int size = (int) (Math.random() * 100) % this.maxSize + 1;
            List<Integer> data = factory.consume(size, id);
        }
    }
}
