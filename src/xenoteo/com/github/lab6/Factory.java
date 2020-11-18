package xenoteo.com.github.lab6;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Factory {
    private final Lock lock;
    private final Condition producersCond;
    private final Condition consumersCond;

    private final List<Integer> empty;
    private final List<Integer> occupied;

    private final int size;

    private final Buffer buffer;

    public Factory(int size){
        this.size = size;

        this.buffer = new Buffer(size);

        this.lock = new ReentrantLock();
        this.producersCond = lock.newCondition();
        this.consumersCond = lock.newCondition();

        this.empty = new LinkedList<>();
        this.occupied = new LinkedList<>();

        for(int i = 0; i < size; i++) empty.add(i);
    }

    public void produce(int val, int producerId){
        lock.lock();
        System.out.printf("Producer %d entered\n", producerId);
        try {
            System.out.printf("[THREADS INFO] %d threads in buffer\n", countNumberOfThreadsInBuffer());
            while (empty.isEmpty()) producersCond.await();
            int position = empty.remove(0);
            buffer.produce(position, val);
            System.out.printf("Producer %d produced value %d to position %d\n", producerId, val, position);
            occupied.add(position);
            consumersCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int consume(int consumerId){
        lock.lock();
        System.out.printf("Consumer %d entered. %d threads in buffer\n", consumerId, countNumberOfThreadsInBuffer());
        try{
            System.out.printf("[THREADS INFO] %d threads in buffer\n", countNumberOfThreadsInBuffer());
            while (occupied.isEmpty()) consumersCond.await();
            int position = occupied.remove(0);
            int val = buffer.consume(position);
            System.out.printf("Consumer %d consumed value %d from position %d\n", consumerId, val, position);
            empty.add(position);
            producersCond.signal();
            return val;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
        return -1;
    }

    private int countNumberOfThreadsInBuffer(){
        return size - empty.size() - occupied.size();
    }
}