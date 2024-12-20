package rehabilitation;

import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue {
	private static final Queue<String> commandQueue = new LinkedList<String>();

    public static synchronized void addCommand(String command) {
        commandQueue.add(command);
        CommandQueue.class.notifyAll();  // Notify waiting threads
    }

    public static synchronized String getCommand() {
        while (commandQueue.isEmpty()) {
            try {
                CommandQueue.class.wait();  // Wait for commands to be added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Handle interruption
            }
        }
        return commandQueue.poll();
    }

    public static synchronized boolean hasCommands() {
        return !commandQueue.isEmpty();
    }
}
