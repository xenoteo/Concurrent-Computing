package xenoteo.com.github.lab7_8_9.monitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Producer counting a provided number of sinuses after each production and stopping activity at the given moment.
 */
public class Producer extends SinusCalculator implements Runnable{
    private final Factory factory;
    private final int id;
    private final int maxSize;
    /**
     * Time for producer to finish activity
     */
    private final long finishTime;

    /**
     * Variable for counting how many productions were performed
     */
    private int count;

    /**
     * Number of sinuses to be counted after each cproduction
     */
    private int sinCount;

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
