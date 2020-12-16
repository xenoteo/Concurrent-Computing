package xenoteo.com.github.lab6;

/**
 * Simple procuder implementation.
 */
public class Producer implements Runnable{
    private int id;
    private Factory factory;

    public Producer(int id, Factory factory){
        this.id = id;
        this.factory = factory;
    }

    @Override
    public void run() {
        while (true){
            int val = (int) (Math.random() * 100);
            factory.produce(val, id);
        }
    }
}
