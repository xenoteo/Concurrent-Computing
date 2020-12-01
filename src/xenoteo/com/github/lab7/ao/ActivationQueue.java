package xenoteo.com.github.lab7.ao;

import xenoteo.com.github.lab7.ao.request.ConsumerRequest;
import xenoteo.com.github.lab7.ao.request.MethodRequest;
import xenoteo.com.github.lab7.ao.request.ProducerRequest;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {
    private final LinkedList<MethodRequest> producerQueue;
    private final LinkedList<MethodRequest> consumerQueue;
    private final Lock lock;
    private final Condition schedulerCondition;

    public ActivationQueue(){
        producerQueue = new LinkedList<>();
        consumerQueue = new LinkedList<>();
        lock = new ReentrantLock();
        schedulerCondition = lock.newCondition();
    }

    public void enqueue(MethodRequest request){
        lock.lock();
        try{
            if (request instanceof ConsumerRequest)
                consumerQueue.add(request);
            else if (request instanceof  ProducerRequest)
                producerQueue.add(request);
            schedulerCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    private MethodRequest tryDequeue(){
        if (!producerQueue.isEmpty() && producerQueue.peek().guard())
            return producerQueue.poll();
        if (!consumerQueue.isEmpty() && consumerQueue.peek().guard())
            return consumerQueue.poll();
        return null;
    }

    public MethodRequest dequeue(){
        lock.lock();
        MethodRequest request = null;
        try{
            while (request == null){
                request = tryDequeue();
                schedulerCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return request;
    }
}
