package xenoteo.com.github.lab7.ao.scheduler;

public class SchedulerRunner implements Runnable{
    private final Scheduler scheduler;
    private final long finishTime;

    public SchedulerRunner(Scheduler scheduler, long finishTime) {
        this.scheduler = scheduler;
        this.finishTime = finishTime;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < finishTime){
            scheduler.dispatch();
        }
    }
}
