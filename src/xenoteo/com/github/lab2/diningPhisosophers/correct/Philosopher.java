package xenoteo.com.github.lab2.diningPhisosophers.correct;

import xenoteo.com.github.lab2.semaphores.CountingSemaphore;
import xenoteo.com.github.lab2.semaphores.Semaphore;

/**
 * Implementing dining philosophers using the help of a valet,
 * who lets in just 4 of 5 philosophers, which prevents from deadlock.
 */
public class Philosopher implements Runnable {
    public static final int PHILOSOPHERS_NUMBER = 5;
    private final int id;
    private CountingSemaphore valet;
    private Semaphore[] forks;

    Philosopher(int id, Semaphore[] forks, CountingSemaphore valet) {
        this.id = id;
        this.valet = valet;
        this.forks = forks;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Philosopher %d is thinking...\n", id);
            int leftFork = id;
            int rightFork = (id + 1) % PHILOSOPHERS_NUMBER;
            try {
                valet.acquire();    // for not letting deadlock
                forks[leftFork].acquire();
                forks[rightFork].acquire();
                System.out.printf("Philosopher %d is eating...\n", id);
                forks[leftFork].release();
                forks[rightFork].release();
                valet.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
