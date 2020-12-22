package xenoteo.com.github.lab10.PCsWithBufferFiniteRunning;

import org.jcsp.lang.*;

import java.util.LinkedList;

/**
 * Buffer class. Manages communication between Producer and Consumer classes.
 */
public class Buffer implements CSProcess {
    private final AltingChannelInputInt[] channelInputs;
    private final AltingChannelInputInt[] channelRequests;
    private final ChannelOutputInt[] channelOutputs;
    private final LinkedList<Integer> buffer;
    private final int size;

    public Buffer(final One2OneChannelInt[] inputs,
                  final One2OneChannelInt[] requests,
                  final One2OneChannelInt[] outputs) {
        channelInputs = new AltingChannelInputInt[inputs.length];
        for (int i = 0; i < inputs.length; i++){
            channelInputs[i] = inputs[i].in();
        }

        channelRequests = new AltingChannelInputInt[requests.length];
        for (int i = 0; i < requests.length; i++){
            channelRequests[i] = requests[i].in();
        }

        channelOutputs = new ChannelOutputInt[outputs.length];
        for (int i = 0; i < outputs.length; i++){
            channelOutputs[i] = outputs[i].out();
        }

        buffer = new LinkedList<>();
        size = 10;
    }

    private Guard[] fillGuards(){
        Guard[] guards = new Guard[channelInputs.length + channelRequests.length];
        int i;
        for (i = 0; i < channelInputs.length; i++){
            guards[i] = channelInputs[i];
        }
        for (; i < guards.length; i++){
            guards[i] = channelRequests[i - channelInputs.length];
        }
        return guards;
    }

    public void run() {
        Guard[] guards = fillGuards();
        Alternative alt = new Alternative(guards);
        int workingProcesses = channelInputs.length + channelOutputs.length;
        while (workingProcesses > 0) {
            int index = alt.select();
            if (index < channelInputs.length){
                if (buffer.size() < size) {
                    ChannelInputInt channelInputInt = channelInputs[index];
                    int item = channelInputInt.read();
                    if (item < 0) workingProcesses--;
                    else {
                        buffer.add(item);
                    }
                }
            }
            else {
                ChannelInputInt channelInputInt2 = channelRequests[index - channelInputs.length];
                ChannelOutputInt channelOutputInt2 = channelOutputs[index - channelInputs.length];
                if (buffer.size() > 0) {
                    channelInputInt2.read();
                    int item = buffer.poll();
                    channelOutputInt2.write(item);
                } else if (workingProcesses <= channelRequests.length) {
                    channelInputInt2.read();
                    channelOutputInt2.write(-1);
                    workingProcesses--;
                }
            }
        }
        System.out.println("Buffer finished");
    }
}