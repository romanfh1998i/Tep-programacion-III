package edu.pucmm.jdbc.games;

import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Snake {

    private static final int DEFAULT_LENGTH = 5;

    private final int id;
    private final Session session;
    private final Deque<Location> tail = new ArrayDeque<>();
    private final String hexColor;
    private Direction direction;
    private int length = DEFAULT_LENGTH;
    private Location head;

    public Snake(int id, Session session) {
        this.id = id;
        this.session = session;
        this.hexColor = SnakeWebSocket.getRandomHexColor();
        resetState();
    }

    private void resetState() {
        this.direction = Direction.NONE;
        this.head = SnakeWebSocket.getRandomLocation();
        this.tail.clear();
        this.length = DEFAULT_LENGTH;
    }

    private synchronized void kill() {
        resetState();
        sendMessage("{'type': 'dead'}");
    }

    private synchronized void reward() {
        length++;
        sendMessage("{'type': 'kill'}");
    }


    protected void sendMessage(String msg) {
        try {
            session.getRemote().sendString(msg);
        } catch (Exception ioe) {
            CloseStatus cr = new CloseStatus(CloseStatus.MAX_REASON_PHRASE, ioe.getMessage());
            try {
                session.close(cr);
            } catch (Exception ioe2) {
                // Ignore 
            }
        }
    }

    public synchronized void update(Collection<Snake> snakes) {
        Location nextLocation = head.getAdjacentLocation(direction);
        if (nextLocation.x >= SnakeWebSocket.PLAYFIELD_WIDTH) {
            nextLocation.x = 0;
        }
        if (nextLocation.y >= SnakeWebSocket.PLAYFIELD_HEIGHT) {
            nextLocation.y = 0;
        }
        if (nextLocation.x < 0) {
            nextLocation.x = SnakeWebSocket.PLAYFIELD_WIDTH;
        }
        if (nextLocation.y < 0) {
            nextLocation.y = SnakeWebSocket.PLAYFIELD_HEIGHT;
        }
        if (direction != Direction.NONE) {
            tail.addFirst(head);
            if (tail.size() > length) {
                tail.removeLast();
            }
            head = nextLocation;
        }

        handleCollisions(snakes);
    }

    private void handleCollisions(Collection<Snake> snakes) {
        for (Snake snake : snakes) {
            boolean headCollision = id != snake.id && snake.getHead().equals(head);
            boolean tailCollision = snake.getTail().contains(head);
            if (headCollision || tailCollision) {
                kill();
                if (id != snake.id) {
                    snake.reward();
                }
            }
        }
    }

    public synchronized Location getHead() {
        return head;
    }

    public synchronized Collection<Location> getTail() {
        return tail;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    public synchronized String getLocationsJson() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("{x: %d, y: %d}",
                head.x, head.y));
        for (Location location : tail) {
            sb.append(',');
            sb.append(String.format("{x: %d, y: %d}", location.x, location.y));
        }
        return String.format("{'id':%d,'body':[%s]}", id, sb.toString());
    }

    public int getId() {
        return id;
    }

    public String getHexColor() {
        return hexColor;
    }
}
