package xenoteo.com.github.lab7_8_9.ao.clients;

import xenoteo.com.github.lab7_8_9.ao.Proxy;
import xenoteo.com.github.lab7_8_9.ao.future.ConsumerFuture;

import java.util.Arrays;
import java.util.List;

/**
 * Consumer counting sinuses while waiting for the consumption to be finished.
 *
 * @see xenoteo.com.github.lab7_8_9.ao.future.Future
 * @see Proxy
 * @see SinusCalculator
 */
public class Consumer extends SinusCalculator implements Runnable{
    private final int id;
    private final int maxSize;
    private final Proxy proxy;
    private final long finishTime;
    private int count;
    private int sinCount;

    public Consumer(int id, int maxSize, Proxy proxy, long finishTime) {
        this.id = id;
        this.maxSize = maxSize;
        this.proxy = proxy;
        this.finishTime = finishTime;
        count = 0;
        sinCount = 0;
    }

    @Override
    public void run() {
        while(System.currentTimeMillis() < finishTime){
            int n = (int) (Math.random() * 100) % this.maxSize + 1;
            ConsumerFuture future = (ConsumerFuture) proxy.consume(n);
            int sinCount = countSinuses(future);
            List<Integer> consumedElements = future.getResult();
            System.out.printf("Consumer %d consumed elements %s; while waiting calculated %d random sinuses\n",
                    id, Arrays.toString(consumedElements.toArray()), sinCount);
            this.sinCount += sinCount;
            count++;
        }
    }

    public int getCount() {
        return count;
    }

    public int getSinCount() {
        return sinCount;
    }
}
