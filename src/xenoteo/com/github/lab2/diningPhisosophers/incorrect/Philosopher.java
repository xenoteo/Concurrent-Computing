package xenoteo.com.github.lab2.diningPhisosophers.incorrect;

import xenoteo.com.github.lab2.semaphores.Semaphore;

/**
 * Implementing dining philosophers using naive approach.
 */
public class Philosopher implements Runnable {
    public static final int PHILOSOPHERS_NUMBER = 5;
    private final int id;
    private Semaphore[] forks;

    Philosopher(int id, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Philosopher %d is thinking...\n", id);
            int leftFork = id;
            int rightFork = (id + 1) % PHILOSOPHERS_NUMBER;
            try {
                forks[leftFork].acquire();
                forks[rightFork].acquire();
                System.out.printf("Philosopher %d is eating...\n", id);
                forks[leftFork].release();
                forks[rightFork].release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
