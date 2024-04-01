import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyInput implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); // Gets the code of the key that was pressed.

        // Retrieves a reference to the player entity from the game's handler.
        // This assumes Game.getHandler().getPlayer() method exists and returns the player entity correctly.
        Entity player = Game.getHandler().getPlayer();

        // Check which key was pressed and update the player's velocity or state accordingly.
        if (player != null) {
            switch (key) {
                case KeyEvent.VK_UP:
                    // Initiates jumping only if the player is not already jumping.
                    if (!player.jumping) {
                        player.jumping = true;
                        player.gravity = 7.0; // Set initial jump force.
                        // Consider having a method in player to handle the start of a jump for encapsulation.
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setVelX(5); // Moves the player to the right.
                    player.facing = 1;
                    break;
                case KeyEvent.VK_LEFT:
                    player.setVelX(-5); // Moves the player to the left.
                    player.facing = 0;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode(); // Gets the code of the key that was released.

        // Retrieves a reference to the player entity from the game's handler.
        Entity player = Game.getHandler().getPlayer();

        // When the key is released, reset the player's velocity to 0 to stop movement.
        if (player != null) {
            switch (key) {
                case KeyEvent.VK_UP:
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_LEFT:
                    player.setVelX(0); // Stops horizontal movement.
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // This method is part of the KeyListener interface but is not used in this context.
    }
}
