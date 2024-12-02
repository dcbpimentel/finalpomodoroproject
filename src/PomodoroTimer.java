import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PomodoroApp {
    private static int minutes = 25; // Default timer duration
    private static int seconds = 0;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new TimerHandler());
        server.createContext("/start", new StartHandler());
        server.createContext("/reset", new ResetHandler());
        server.setExecutor(null);
        System.out.println("Server running on http://localhost:8000");
        server.start();
    }

    static class TimerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws java.io.IOException {
            String response = getHTMLPage();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class StartHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws java.io.IOException {
            startTimer();
            String response = getHTMLPage();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ResetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws java.io.IOException {
            resetTimer();
            String response = getHTMLPage();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static void startTimer() {
        // Logic for starting the timer
        new Thread(() -> {
            try {
                while (minutes > 0 || seconds > 0) {
                    Thread.sleep(1000);
                    if (seconds == 0) {
                        minutes--;
                        seconds = 59;
                    } else {
                        seconds--;
                    }
                    System.out.printf("Time Remaining: %02d:%02d\n", minutes, seconds);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void resetTimer() {
        minutes = 25;
        seconds = 0;
    }

    private static String getHTMLPage() {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head><title>Pomodoro Timer</title></head>" +
                "<body>" +
                "<h1>Academic Pomodoro Timer</h1>" +
                "<h2>Time Remaining: " + String.format("%02d:%02d", minutes, seconds) + "</h2>" +
                "<form action='/start' method='POST'><button type='submit'>Start</button></form>" +
                "<form action='/reset' method='POST'><button type='submit'>Reset</button></form>" +
                "</body>" +
                "</html>";
    }
}
