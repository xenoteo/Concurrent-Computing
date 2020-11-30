package xenoteo.com.github.lab7.ao.future;

public abstract class Future {
    protected boolean finished = false;

    public void finish(){
        finished = true;
    }

    public boolean isFinished(){
        return finished;
    }
}
