package xenoteo.com.github.lab5.booleanVariables;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Factory, where producers produce and consumers consume.
 */
public class Factory {
    private final ReentrantLock lock;

    private final Condition restConsumers;
    private final Condition firstConsumer;
    private boolean firstConsumerWaits;

    private final Condition restProducers;
    private final Condition firstProducer;
    private boolean firstProducerWaits;

    private final int size;
    private final LinkedList<Integer> stock;

    public Factory(int size) {
        this.size = size;
        stock = new LinkedList<>();

        lock = new ReentrantLock();
        restConsumers = lock.newCondition();
        firstConsumer = lock.newCondition();
        restProducers = lock.newCondition();
        firstProducer = lock.newCondition();
    }

    public void produce(List<Integer> data, int id){
        lock.lock();
        System.out.printf("Producer %d is entering and is going to produce %d elements. " +
                "There are %d elements in the stock.\n", id, data.size(), stock.size());
        System.out.printf("[THREADS INFO] %d threads in firstProducer, %d threads in restProducer, " +
                        "%d threads in firstConsumer, %d threads in restConsumer\n",
                lock.getWaitQueueLength(firstProducer), lock.getWaitQueueLength(restProducers),
                lock.getWaitQueueLength(firstConsumer), lock.getWaitQueueLength(restConsumers));
        try {
            while (firstProducerWaits) {
                restProducers.await();
                System.out.printf("Producer %d is waiting for the first producer to finish...\n", id);
            }
            while (!hasEnoughSpace(data.size())){
                firstProducerWaits = true;
                firstProducer.await();
                System.out.printf("Producer %d is waiting for the space to be freed...\n", id);
            }
            firstProducerWaits = false;
            stock.addAll(data);
            System.out.printf("Producer %d produced values %s\n", id, Arrays.toString(data.toArray()));
            firstConsumer.signal();
            restProducers.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> consume(int size, int id){
        lock.lock();
        System.out.printf("Consumer %d is entering and is going to consume %d elements. " +
                "There are %d elements in the stock.\n", id, size, stock.size());
        System.out.printf("[THREADS INFO] %d threads in firstProducer, %d threads in restProducer, " +
                        "%d threads in firstConsumer, %d threads in restConsumer\n",
                lock.getWaitQueueLength(firstProducer), lock.getWaitQueueLength(restProducers),
                lock.getWaitQueueLength(firstConsumer), lock.getWaitQueueLength(restConsumers));
        try {
            while (firstConsumerWaits) {
                restConsumers.await();
                System.out.printf("Consumer %d is waiting for the first consumer to finish...\n", id);
            }
            while (!hasEnoughElements(size)){
                firstConsumerWaits = true;
                firstConsumer.await();
                System.out.printf("Consumer %d is waiting for the data to occur...\n", id);
            }
            firstConsumerWaits = false;
            List<Integer> data = new LinkedList<>();
            for (int i = 0; i < size; i++){
                data.add(stock.poll());
            }
            System.out.printf("Consumer %d consumed values %s\n", id, Arrays.toString(data.toArray()));
            firstProducer.signal();
            restConsumers.signal();
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            lock.unlock();
        }
    }

    private boolean hasEnoughSpace(int size){
        return (this.size - stock.size() >= size);
    }

    private boolean hasEnoughElements(int size){
        return (stock.size() >= size);
    }
}