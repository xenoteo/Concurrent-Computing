package xenoteo.com.github.lab4;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Factory, where producers produce and consumers consume.
 */
public class Factory {
    private final Lock lock;
    private final Condition consumerLock;
    private final Condition producerLock;

    private final int size;
    private LinkedList<Integer> stock;

    public Factory(int size) {
        this.size = size;
        stock = new LinkedList<>();

        lock = new ReentrantLock();
        consumerLock = lock.newCondition();
        producerLock = lock.newCondition();
    }

    public void produce(int val, int id){
        lock.lock();
        try {
            while (isFull()){
                producerLock.await();
                System.out.printf("Producer %d is waiting...\n", id);
            }
            stock.add(val);
            System.out.printf("Producer %d produced value %d\n", id, val);
            consumerLock.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int consume(int id){
        lock.lock();
        try {
            while (isEmpty()){
                consumerLock.await();
                System.out.printf("Consumer %d is waiting...\n", id);
            }
            int val = stock.poll();
            System.out.printf("Consumer %d consumed value %d\n", id, val);
            producerLock.signal();
            return val;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            lock.unlock();
        }
    }

    private boolean isEmpty(){
        return stock.isEmpty();
    }

    private boolean isFull(){
        return stock.size() == size;
    }
}