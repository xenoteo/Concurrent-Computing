package xenoteo.com.github.lab1;

/**
 * Simple counter implementation.
 */
public class Counter {
    private int val = 0;

    public Counter() {
    }

    public Counter(int val) {
        this.val = val;
    }

    public void increment(){
        val++;
    }

    public void decrement(){
        val--;
    }

    public int getVal() {
        return val;
    }
}
