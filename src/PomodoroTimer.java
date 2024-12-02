import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private static int minutes = 25;
    private static int seconds = 0;
    private static Timer timer = new Timer();

    public static void main(String[] args) {
        System.out.println("Pomodoro Timer Started!");
        startTimer();
    }

    private static void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (minutes == 0 && seconds == 0) {
                    System.out.println("Time's up! Take a break.");
                    timer.cancel();
                } else {
                    if (seconds == 0) {
                        minutes--;
                        seconds = 59;
                    } else {
                        seconds--;
                    }
                    System.out.printf("Time Remaining: %02d:%02d%n", minutes, seconds);
                }
            }
        };

        // Schedule the task to run every second
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
