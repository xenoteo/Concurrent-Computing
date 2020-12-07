package xenoteo.com.github.lab7.monitor;

import java.util.Arrays;
import java.util.List;

public class Consumer extends SinusCalculator implements Runnable{
    private final Factory factory;
    private final int id;
    private final int maxSize;
    private final long finishTime;
    private int count;
    private int sinCount;

    public Consumer(int id, Factory factory, int maxSize, int sinCount, long finishTime) {
        super(sinCount);
        this.factory = factory;
        this.maxSize = maxSize;
        this.id = id;
        this.finishTime = finishTime;
        count = 0;
        sinCount = 0;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < finishTime) {
            int size = (int) (Math.random() * 100) % this.maxSize + 1;
            List<Integer> data = factory.consume(size, id);
            int sinCount = countSinuses();
            System.out.printf("Consumer %d consumed elements %s and calculated %d random sinuses\n",
                    id, Arrays.toString(data.toArray()), sinCount);
            count++;
            this.sinCount += sinCount;
        }
    }

    public int getCount() {
        return count;
    }

    public int getSinCount() {
        return sinCount;
    }
}
