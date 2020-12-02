package xenoteo.com.github.lab7.ao.clients;

import xenoteo.com.github.lab7.ao.Proxy;
import xenoteo.com.github.lab7.ao.future.ProducerFuture;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Producer extends SinusCalculator implements Runnable{
    private final int id;
    private final int maxSize;
    private final Proxy proxy;

    public Producer(int id, int maxSize, Proxy proxy) {
        this.id = id;
        this.maxSize = maxSize;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        while(true) {
            List<Integer> elements = produceElements();
            ProducerFuture future = (ProducerFuture) proxy.produce(elements);
            int count = countSinuses(future);
            System.out.printf("Producer %d produced elements %s; while waiting calculated %d random sinuses\n",
                    id, Arrays.toString(elements.toArray()), count);
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
}
