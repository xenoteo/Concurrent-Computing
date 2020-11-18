package xenoteo.com.github.lab6;

import java.util.HashMap;
import java.util.Map;

public class Buffer {
    private final int size;
    private final Map<Integer, Integer> buffer;

    public Buffer(int limit){
        this.size = limit;
        this.buffer = new HashMap<>();
    }

    public void produce(int position, int val){
        buffer.put(position, val);
    }

    public int consume(int position){
        return buffer.remove(position);
    }

    public int getSize(){ return this.size; }
}
