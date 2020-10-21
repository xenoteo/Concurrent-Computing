package xenoteo.com.github.lab3;

import java.util.LinkedList;

/**
 * Factory, where producers produce and consumers consume.
 */
public class Factory {
    private int size;
    private LinkedList<Integer> stock;

    public Factory(int size) {
        this.size = size;
        stock = new LinkedList<>();
    }

    public synchronized void produce(int val, int id){
        try {
            while (isFull()){
                wait();
                System.out.printf("Producer %d is waiting...\n", id);
            }
            stock.add(val);
            System.out.printf("Producer %d produced value %d\n", id, val);
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int consume(int id){
        try {
            while (isEmpty()){
                wait();
                System.out.printf("Consumer %d is waiting...\n", id);
            }
            int val = stock.poll();
            System.out.printf("Consumer %d consumed value %d\n", id, val);
            notify();
            return val;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean isEmpty(){
        return stock.isEmpty();
    }

    private boolean isFull(){
        return stock.size() >= size;
    }
}