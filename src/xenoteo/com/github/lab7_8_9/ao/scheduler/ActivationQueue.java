package xenoteo.com.github.lab7_8_9.ao.scheduler;

import xenoteo.com.github.lab7_8_9.ao.request.ConsumerRequest;
import xenoteo.com.github.lab7_8_9.ao.request.MethodRequest;
import xenoteo.com.github.lab7_8_9.ao.request.ProducerRequest;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An Activation Queue maintains a bounded buffer of pending Method Requests created by the Proxy.
 * This queue keeps track of which Method Requests to execute. It also decouples the client thread from
 * the servant thread so the two threads can run concurrently.
 *
 * @see MethodRequest
 * @see Scheduler
 * @see xenoteo.com.github.lab7_8_9.ao.Proxy
 */
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
        try{
            MethodRequest request = tryDequeue();
            while (request == null){
                schedulerCondition.await();
                request = tryDequeue();
            }
            return request;
        } catch (InterruptedException e) {
            System.err.println("Task interrupted");
        } finally {
            lock.unlock();
        }
        return null;
    }
}
