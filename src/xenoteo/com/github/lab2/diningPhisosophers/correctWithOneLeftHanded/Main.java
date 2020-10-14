package xenoteo.com.github.lab2.diningPhisosophers.correctWithOneLeftHanded;

import xenoteo.com.github.lab2.Semaphore;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of properly eating philosophers.
 */
public class Main {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[Philosopher.PHILOSOPHERS_NUMBER];
        Semaphore[] forks = new Semaphore[Philosopher.PHILOSOPHERS_NUMBER];

        List<Thread> diningPhilosophers = new ArrayList<>();

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i, forks);
            forks[i] = new Semaphore();
            Thread thread = new Thread(philosophers[i]);
            thread.start();
            diningPhilosophers.add(thread);
        }

        try {
            for (int i = 0; i < Philosopher.PHILOSOPHERS_NUMBER; i++){
                diningPhilosophers.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
