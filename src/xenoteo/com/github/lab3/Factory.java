package xenoteo.com.github.lab3;

import java.util.LinkedList;

public class Factory {
    private int size;
    private LinkedList<Integer> stock;

    public Factory(int size) {
        this.size = size;
        stock = new LinkedList<>();
    }

    public synchronized void produce(int val){
        try {
            while (isFull()){
                wait();
                System.out.println("Producer is waiting...");
            }
            stock.add(val);
            System.out.printf("produced value %d\n", val);
            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int consume(){
        try {
            while (isEmpty()){
                wait();
                System.out.println("Consumer is waiting...");
            }
            int val = stock.poll();
            System.out.printf("consumed value %d\n", val);
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