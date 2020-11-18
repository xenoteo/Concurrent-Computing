package xenoteo.com.github.lab6;

public class Consumer implements Runnable{
    private int id;
    private Factory factory;

    public Consumer(int id, Factory factory){
        this.id = id;
        this.factory = factory;
    }

    @Override
    public void run() {
        while (true){
            int val = factory.consume(id);
        }
    }
}
