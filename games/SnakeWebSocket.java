package edu.pucmm.jdbc.games;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.awt.*;
import java.io.EOFException;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;

@WebSocket
public class SnakeWebSocket {

    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;

    private static final AtomicInteger snakeIds = new AtomicInteger(0);
    private static final Random random = new Random();

    private final int id;
    private Snake snake;

    public SnakeWebSocket() {
        this.id = snakeIds.getAndIncrement();
    }

    public static String getRandomHexColor() {
        float hue = random.nextFloat();
        // sat between 0.1 and 0.3
        float saturation = (random.nextInt(2000) + 1000) / 10000f;
        float luminance = 0.9f;
        Color color = Color.getHSBColor(hue, saturation, luminance);
        return '#' + Integer.toHexString(
                (color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }

    public static Location getRandomLocation() {
        int x = roundByGridSize(random.nextInt(PLAYFIELD_WIDTH));
        int y = roundByGridSize(random.nextInt(PLAYFIELD_HEIGHT));
        return new Location(x, y);
    }

    private static int roundByGridSize(int value) {
        value = value + (GRID_SIZE / 2);
        value = value / GRID_SIZE;
        value = value * GRID_SIZE;
        return value;
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        this.snake = new Snake(id, session);
        SnakeTimer.addSnake(snake);
        StringBuilder sb = new StringBuilder();
        for (Iterator<Snake> iterator = SnakeTimer.getSnakes().iterator();
                iterator.hasNext();) {
            Snake snake = iterator.next();
            sb.append(String.format("{id: %d, color: '%s'}", snake.getId(), snake.getHexColor()));
            if (iterator.hasNext()) {
                sb.append(',');
            }
        }
        SnakeTimer.broadcast(String.format("{'type': 'join','data':[%s]}",
                sb.toString()));
    }

    @OnWebSocketMessage
    public void onTextMessage(String message) {
        if ("west".equals(message)) {
            snake.setDirection(Direction.WEST);
        } else if ("north".equals(message)) {
            snake.setDirection(Direction.NORTH);
        } else if ("east".equals(message)) {
            snake.setDirection(Direction.EAST);
        } else if ("south".equals(message)) {
            snake.setDirection(Direction.SOUTH);
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int val, String strValue) {
        SnakeTimer.removeSnake(snake);
        SnakeTimer.broadcast(String.format("{'type': 'leave', 'id': %d}", id));
    }

    @OnWebSocketError
    public void onError(Throwable t) throws Throwable {
        // Most likely cause is a user closing their browser. Check to see if 
        // the root cause is EOF and if it is ignore it. 
        // Protect against infinite loops. 
        int count = 0;
        Throwable root = t;
        while (root.getCause() != null && count < 20) {
            root = root.getCause();
            count++;
        }
        if (root instanceof EOFException) {
            // Assume this is triggered by the user closing their browser and 
            // ignore it. 
        } else {
            throw t;
        }
    }
}
