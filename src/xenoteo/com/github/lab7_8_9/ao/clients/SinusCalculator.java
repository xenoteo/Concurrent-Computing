package xenoteo.com.github.lab7_8_9.ao.clients;

import xenoteo.com.github.lab7_8_9.ao.future.Future;

public abstract class SinusCalculator {
    private double calculateRandomSinus(){
        return Math.sin(Math.random() * 100);
    }

    public int countSinuses(Future future){
        int count = 0;
        while (!future.isFinished()){
            calculateRandomSinus();
            count++;
        }
        return count;
    }
}
