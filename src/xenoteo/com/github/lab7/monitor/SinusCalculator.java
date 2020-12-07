package xenoteo.com.github.lab7.monitor;


public abstract class SinusCalculator {
    protected int sinusesCount = 0;

    public SinusCalculator(int sinusesCount) {
        this.sinusesCount = sinusesCount;
    }

    private double calculateRandomSinus(){
        return Math.sin(Math.random() * 100);
    }

    public int countSinuses(){
        for (int i = 0; i < sinusesCount; i++)
            calculateRandomSinus();
        return sinusesCount;
    }
}
