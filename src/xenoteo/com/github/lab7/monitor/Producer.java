package xenoteo.com.github.lab7.monitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Producer extends SinusCalculator implements Runnable{
    private final Factory factory;
    private final int id;
    private final int maxSize;
    private final long finishTime;
    private int count;

    public Producer(int id, Factory factory, int maxSize, int sinCount, long finishTime) {
        super(sinCount);
        this.factory = factory;
        this.maxSize = maxSize;
        this.id = id;
        this.finishTime = finishTime;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < finishTime) {
            int size = (int) (Math.random() * 100) % this.maxSize + 1;
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < size; i++){
                data.add((int) (Math.random() * 100));
            }
            factory.produce(data, id);
            int sinCount = countSinuses();
            System.out.printf("Producer %d produced elements %s and calculated %d random sinuses\n",
                    id, Arrays.toString(data.toArray()), sinCount);
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
