package xenoteo.com.github.lab2.diningPhisosophers.correctWithOneLeftHanded;

import xenoteo.com.github.lab2.semaphores.Semaphore;

/**
 * Implementing dining philosophers where one of the philosophers is left-handed,
 * that is he takes the forks in the reversed order relatively to others.
 */
public class Philosopher implements Runnable {
    public static final int PHILOSOPHERS_NUMBER = 5;
    private final int id;
    private Semaphore[] forks;

    public Philosopher(int id, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Philosopher %d is thinking...\n", id);
            // philosopher with id 0 is left-handed
            int leftFork = id == 0 ? id + 1 : id;
            int rightFork = id == 0 ? id : (id + 1) % PHILOSOPHERS_NUMBER;
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