package xenoteo.com.github.lab5;

import java.util.LinkedList;
import java.util.List;

/**
 * Producer producing a random number.
 */
public class Producer implements Runnable{
    private Factory factory;
    private int id;
    private int maxSize;

    public Producer(int id, Factory factory, int maxSize) {
        this.factory = factory;
        this.maxSize = maxSize;
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            int size = (int) (Math.random() * 100) % this.maxSize + 1;
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < size; i++){
                data.add((int) (Math.random() * 100));
            }
            factory.produce(data, id);
        }
    }
}
