import java.awt.Graphics;

public class Player extends Entity {

    private int frame = 0;
    private int frameDelay = 0;
    private boolean animate = false;
    
    // Constructor: Initializes a new player with the given properties.
    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        if (facing == 0) {
            g.drawImage(Game.getPlayer()[frame+2].getBufferedImage(), x, y, width,height,null);
        } else {
            g.drawImage(Game.getPlayer()[frame].getBufferedImage(), x, y, width,height,null);
        }
        
    }

    @Override
    public void update() {
        // Updates the player's position based on their current velocity.
        x += velX;
        y += velY;

        if(velX!=0) 
            animate = true;
        else 
            animate = false;

        // Collision detection with tiles. This could be optimized further by only checking nearby tiles.
        for (Entity t : handler.getTile()) {
            if (!t.isSolid()) break; // Skip if the tile is not solid.
            if(t.getId() == Id.wall) {
                collisionWithWall(t); // Handle collision with a wall.
            }
        }

        for (int i = 0; i< handler.getEntities().size(); i++ ) {
            Entity e = handler.getEntities().get(i);
            if (e.getId() == Id.mashroom) {
                if(getBounds().intersects(e.getBounds())) {
                    width*=2;
                    height*=2;
                    setX(getX()-width);
                    setY(getY()-height);
                    e.die();
                }
            }

        }
        
        applyGravity(); // Apply gravity to simulate jumping and falling.

        if (animate) {
            frameDelay++;
        if (frameDelay>=3) {
            frame++;
            if (frame>=2) {
                frame = 0;
            }
            frameDelay = 0;
        }
        }
    }
    
    // Handles collision with wall tiles.
    private void collisionWithWall(Entity t) {
        // Checks collision on each side of the player and adjusts position and velocity accordingly.
        if (getBoundsTop().intersects(t.getBounds())) {
            setVelY(0); // Stop vertical movement.
            if (jumping) {
                jumping = false;
                gravity = 0.8;
                falling = true;
            }
        }

        if (getBoundsBottom().intersects(t.getBounds())) {
            setVelY(0); // Stop vertical movement.
            if(falling) falling = false; // Player is no longer falling.
        } else {
            if (!falling && !jumping) {
                gravity = 0.8;
                falling = true;
            }
        }

        if (getBoundsLeft().intersects(t.getBounds())) {
            setVelX(0); // Stop horizontal movement to the left.
            x = t.getX() + t.width; // Reposition to avoid embedding into the tile.
        }

        if (getBoundsRight().intersects(t.getBounds())) {
            setVelX(0); // Stop horizontal movement to the right.
            x = t.getX() - width; // Reposition to avoid embedding into the tile.
        }
    }
    
    // Applies gravity to the player to handle jumping and falling mechanics.
    private void applyGravity() {
        if (jumping) {
            gravity -= 0.1; // Decrease gravity to simulate the jump's upward movement.
            setVelY((int) -gravity); // Apply the updated velocity.
            if(gravity <= 0.0) {
                jumping = false; // End the jump.
                falling = true; // Start falling.
            }
        }
        if (falling) {
            gravity += 0.1; // Increase gravity to simulate falling.
            setVelY((int) gravity); // Apply the updated velocity.
        }

    }
}
