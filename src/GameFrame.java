import javax.swing.*;
import java.awt.*;
import java.io.Serial;

/**
 * This class represents the main frame of the game
 */
public class GameFrame extends JFrame {

    // A unique identifier for serialization
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * GameFrame constructor
     */
    public GameFrame() {
        // Create a new GamePanel
        GamePanel panel = new GamePanel();

        // Set the layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add the GamePanel to the center of the frame
        this.add(panel, BorderLayout.CENTER);

        // Set the title of the frame to "Snake game"
        this.setTitle("Snake game");

        // Set the default close operation to exit on close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame not resizable
        this.setResizable(false);

        // Pack the components in the frame
        this.pack();

        // Make the frame visible
        this.setVisible(true);

        // Center the frame on the screen
        this.setLocationRelativeTo(null);
    }
}
