package xenoteo.com.github.lab7.ao.clients;

import xenoteo.com.github.lab7.ao.Proxy;
import xenoteo.com.github.lab7.ao.future.ConsumerFuture;

import java.util.Arrays;
import java.util.List;

public class Consumer extends SinusCalculator implements Runnable{
    private final int id;
    private final int maxSize;
    private final Proxy proxy;

    public Consumer(int id, int maxSize, Proxy proxy) {
        this.id = id;
        this.maxSize = maxSize;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        while(true){
            int n = (int) (Math.random() * 100) % this.maxSize + 1;
            ConsumerFuture future = (ConsumerFuture) proxy.consume(n);
            int count = countSinuses(future);
            List<Integer> consumedElements = future.getResult();
            System.out.printf("Consumer %d consumed elements %s; while waiting calculated %d random sinuses\n",
                    id, Arrays.toString(consumedElements.toArray()), count);
        }

    }
}
