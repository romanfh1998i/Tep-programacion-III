package edu.pucmm.jdbc.games;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Sets up the timer for the multi-player snake game WebSocket example.
 */
public class SnakeTimer {

    private static final Logger log = LoggerFactory.getLogger(SnakeTimer.class);
    private static final long TICK_DELAY = 100;
    private static final ConcurrentHashMap<Integer, Snake> snakes =
            new ConcurrentHashMap<Integer, Snake>();
    private static Timer gameTimer = null;

    protected static synchronized void addSnake(Snake snake) {
        if (snakes.size() == 0) {
            startTimer();
        }
        snakes.put(snake.getId(), snake);
    }


    protected static Collection<Snake> getSnakes() {
        return Collections.unmodifiableCollection(snakes.values());
    }


    protected static synchronized void removeSnake(Snake snake) {
        snakes.remove(snake.getId());
        if (snakes.size() == 0) {
            stopTimer();
        }
    }


    protected static void tick() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = SnakeTimer.getSnakes().iterator();
             iterator.hasNext(); ) {
            Snake snake = iterator.next();
            snake.update(SnakeTimer.getSnakes());
            sb.append(snake.getLocationsJson());
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        broadcast(String.format("{'type': 'update', 'data' : [%s]}",
                sb.toString()));
    }

    protected static void broadcast(String message) {
        for (Snake snake : SnakeTimer.getSnakes()) {
            try {
                snake.sendMessage(message);
            } catch (IllegalStateException ise) {
                // An ISE can occur if an attempt is made to write to a 
                // WebSocket connection after it has been closed. The 
                // alternative to catching this exception is to synchronise 
                // the writes to the clients along with the addSnake() and 
                // removeSnake() methods that are already synchronised. 
            }
        }
    }


    public static void startTimer() {
        gameTimer = new Timer(SnakeTimer.class.getSimpleName() + " Timer");
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                } catch (RuntimeException e) {
                    log.error("Caught to prevent timer from shutting down", e);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }


    public static void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }
}
