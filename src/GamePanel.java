import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Random;

/**
 * Represents the game panel where the Snake game is displayed.
 */
public class GamePanel extends JPanel implements ActionListener {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int UNIT_SIZE = 20;
    private static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    // Snake's body x & y coordinators
    private final int[] x = new int[NUMBER_OF_UNITS];
    private final int[] y = new int[NUMBER_OF_UNITS];

    private int length = 5;
    private int foodEaten;
    private int foodX;
    private int foodY;
    private Direction direction = Direction.RIGHT;
    private boolean running = false;
    private final Random random = new Random();
    private final Timer timer;


    /**
     * Subclass handles key events to change the direction of the snake.
     */
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != Direction.RIGHT) {
                        direction = Direction.LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != Direction.LEFT) {
                        direction = Direction.RIGHT;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != Direction.DOWN) {
                        direction = Direction.UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != Direction.UP) {
                        direction = Direction.DOWN;
                    }
                    break;
            }
        }
    }

    /**
     * Constructs a new GamePanel.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        play();
        timer = new Timer(80, this);
        timer.start();
    }

    /**
     * Initiates the game by adding the initial food.
     */
    private void play() {
        addFood();
        running = true;
    }

    /**
     * Overrides the paintComponent method to draw the game components.
     *
     * @param graphics The Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    /**
     * Moves the snake and updates the game state.
     */
    private void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case LEFT:
                x[0] -= UNIT_SIZE;
                break;
            case RIGHT:
                x[0] += UNIT_SIZE;
                break;
            case UP:
                y[0] -= UNIT_SIZE;
                break;
            case DOWN:
                y[0] += UNIT_SIZE;
                break;
        }
    }

    /**
     * Checks if the snake has eaten the food.
     */
    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            length++;
            foodEaten++;
            addFood();
        }
    }

    /**
     * Draws the game components on the panel.
     *
     * @param graphics The Graphics object used for drawing.
     */
    private void draw(Graphics graphics) {
        if (running) {
            // Set color to yellow for the food
            graphics.setColor(new Color(210, 115, 90));
            graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            // Set color to blue for the snake's head
            graphics.setColor(Color.white);
            graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

            // Set color to a different shade of blue for the snake's body
            for (int i = 1; i < length; i++) {
                graphics.setColor(new Color(40, 200, 150));
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            // Set color to white for the score display
            graphics.setColor(Color.white);
            graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
        } else {
            gameOver(graphics);
        }
    }

    /**
     * Adds food at a random location on the panel.
     */
    private void addFood() {
        foodX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * Checks if the snake has hit itself or the walls.
     */
    private void checkHit() {
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    /**
     * Displays the game over message on the panel.
     *
     * @param graphics The Graphics object used for drawing.
     */
    private void gameOver(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
        metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score" + foodEaten, (WIDTH - metrics.stringWidth("Score" + foodEaten)) / 2, graphics.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkHit();
        }
        repaint();
    }


    /**
     * Enum representing the possible directions of the snake.
     */
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
