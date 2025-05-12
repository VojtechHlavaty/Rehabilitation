package application;

import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue {
    private static final Queue<String> queue = new LinkedList<String>();

    public static void addCommand(String command) {
        queue.add(command);
    }

    // A very simple blocking getCommand method (in production you’d want proper thread synchronization)
    public static String getCommand() throws InterruptedException {
        while (queue.isEmpty()) {
            Thread.sleep(10);
        }
        return queue.poll();
    }

    public static void clear() {
        queue.clear();
    }
}
