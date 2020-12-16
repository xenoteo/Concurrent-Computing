package xenoteo.com.github.lab7_8_9.ao.clients;

import xenoteo.com.github.lab7_8_9.ao.Proxy;
import xenoteo.com.github.lab7_8_9.ao.future.ProducerFuture;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Producer counting sinuses while waiting for the production to be finished.
 *
 * @see xenoteo.com.github.lab7_8_9.ao.future.Future
 * @see Proxy
 * @see SinusCalculator
 */
public class Producer extends SinusCalculator implements Runnable{
    private final int id;
    private final int maxSize;
    private final Proxy proxy;
    private final long finishTime;
    private int count;
    private int sinCount;

    public Producer(int id, int maxSize, Proxy proxy, long finishTime) {
        this.id = id;
        this.maxSize = maxSize;
        this.proxy = proxy;
        this.finishTime = finishTime;
        count = 0;
        sinCount = 0;
    }

    @Override
    public void run() {
        while(System.currentTimeMillis() < finishTime) {
            List<Integer> elements = produceElements();
            ProducerFuture future = (ProducerFuture) proxy.produce(elements);
            int sinCount = countSinuses(future);
            System.out.printf("Producer %d produced elements %s; while waiting calculated %d random sinuses\n",
                    id, Arrays.toString(elements.toArray()), sinCount);
            this.sinCount += sinCount;
            count++;
        }
    }

    private List<Integer> produceElements(){
        int size = (int) (Math.random() * 100) % this.maxSize + 1;
        List<Integer> data = new LinkedList<>();
        for (int i = 0; i < size; i++){
            data.add((int) (Math.random() * 100) + 1);
        }
        return data;
    }

    public int getCount() {
        return count;
    }

    public int getSinCount() {
        return sinCount;
    }
}
