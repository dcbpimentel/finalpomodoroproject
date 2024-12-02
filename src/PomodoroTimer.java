import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class PomodoroApp {
    private static int minutes = 25; // Default timer duration
    private static int seconds = 0;
    private static boolean running = false;

    public static void main(String[] args) {
        System.out.println("Pomodoro Timer Running...");
        Scanner scanner = new Scanner(System.in);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    if (seconds == 0) {
                        if (minutes == 0) {
                            running = false;
                            System.out.println("\nTime's up!");
                        } else {
                            minutes--;
                            seconds = 59;
                        }
                    } else {
                        seconds--;
                    }
                    System.out.printf("\rTime Remaining: %02d:%02d", minutes, seconds);
                }
            }
        }, 0, 1000);

        while (true) {
            System.out.println("\nCommands: start, pause, reset, quit");
            String command = scanner.nextLine();

            switch (command) {
                case "start":
                    running = true;
                    break;
                case "pause":
                    running = false;
                    break;
                case "reset":
                    running = false;
                    minutes = 25;
                    seconds = 0;
                    System.out.println("Timer reset to 25:00.");
                    break;
                case "quit":
                    timer.cancel();
                    System.out.println("Exiting Pomodoro Timer.");
                    return;
                default:
                    System.out.println("Unknown command. Try again.");
            }
        }
    }
}
