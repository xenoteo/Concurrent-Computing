package xenoteo.com.github.lab3;

/**
 * Producer producing a random number.
 */
public class Producer implements Runnable{
    private Factory factory;
    private int id;

    public Producer(int id, Factory factory) {
        this.factory = factory;
        this.id = id;
    }

    @Override
    public void run() {
        while(true) {
            int val = (int) (Math.random() * 100);
            factory.produce(val, id);
        }
    }
}
