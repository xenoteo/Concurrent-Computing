package xenoteo.com.github.lab7.ao.future;

import java.util.List;

public class ConsumerFuture extends Future{
    private List<Integer> result;

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    public List<Integer> getResult() {
        return result;
    }
}
