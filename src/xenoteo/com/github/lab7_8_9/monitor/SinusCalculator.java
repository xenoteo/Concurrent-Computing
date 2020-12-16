package xenoteo.com.github.lab7_8_9.monitor;

/**
 * Abstract class responsible for calculating a given number of random sinuses.
 */
public abstract class SinusCalculator {
    protected int sinusesCount = 0;

    public SinusCalculator(int sinusesCount) {
        this.sinusesCount = sinusesCount;
    }

    private double calculateRandomSinus(){
        return Math.sin(Math.random() * 100);
    }

    /**
     * Counts random sinuses.
     * @return number of sinuses that was counted
     */
    public int countSinuses(){
        for (int i = 0; i < sinusesCount; i++)
            calculateRandomSinus();
        return sinusesCount;
    }
}
