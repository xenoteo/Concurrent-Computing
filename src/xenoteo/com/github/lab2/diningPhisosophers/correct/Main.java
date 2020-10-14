package xenoteo.com.github.lab2.diningPhisosophers.correct;

import xenoteo.com.github.lab2.CountingSemaphore;
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
        CountingSemaphore valet = new CountingSemaphore(Philosopher.PHILOSOPHERS_NUMBER - 1);

        List<Thread> diningPhilosophers = new ArrayList<>();

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i, forks, valet);
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
