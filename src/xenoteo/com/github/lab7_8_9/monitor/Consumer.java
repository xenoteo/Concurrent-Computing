package xenoteo.com.github.lab7_8_9.monitor;

import java.util.Arrays;
import java.util.List;

/**
 * Consumer counting a provided number of sinuses after each consumption and stopping activity at the given moment.
 */
public class Consumer extends SinusCalculator implements Runnable{
    private final Factory factory;
    private final int id;
    private final int maxSize;

    /**
     * Time for consumer to finish activity
     */
    private final long finishTime;

    /**
     * Variable for counting how many consumptions were performed
     */
    private int count;

    /**
     * Number of sinuses to be counted after each consumption
     */
    private int sinCount;

    public Consumer(int id, Factory factory, int maxSize, int sinCount, long finishTime) {
        super(sinCount);
        this.factory = factory;
        this.maxSize = maxSize;
        this.id = id;
        this.finishTime = finishTime;
        count = 0;
        this.sinCount = 0;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < finishTime) {
            int size = (int) (Math.random() * 100) % this.maxSize + 1;
            List<Integer> data = factory.consume(size, id);
            int sinCount = countSinuses();
            if (data == null) continue;
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
