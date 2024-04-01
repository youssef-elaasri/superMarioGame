import java.awt.Graphics;
import java.util.Random;

public class Mashroom extends Entity{

    private Random random = new Random();

    public Mashroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
        int dir = random.nextInt(2);
        switch (dir) {
            case 0:
                velX = -2;
                break;
            case 1:
                velX = 2;
                break;
        
            default:
                break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.getMashroom().getBufferedImage(), x, y, width, height,null);
    }

    @Override
    public void update() {
        x+= velX;
        y += velY;

         // Collision detection with tiles. This could be optimized further by only checking nearby tiles.
         for (Entity t : handler.getTile()) {
            if (!t.isSolid()) break; // Skip if the tile is not solid.
            if(t.getId() == Id.wall) {
                collisionWithWall(t); // Handle collision with a wall.
            }
        }

        if (falling) {
            gravity += 0.1; // Increase gravity to simulate falling.
            setVelY((int) gravity); // Apply the updated velocity.
        }

    }

    // Handles collision with wall tiles.
    private void collisionWithWall(Entity t) {

        if (getBoundsBottom().intersects(t.getBounds())) {
            setVelY(0); // Stop vertical movement.
            if(falling) falling = false; // Player is no longer falling.
        } else {
            if (!falling) {
                gravity = 0.8;
                falling = true;
            }
        }

        if (getBoundsLeft().intersects(t.getBounds())) {
            setVelX(2);
        }

        if (getBoundsRight().intersects(t.getBounds())) {
            System.out.println("hehe");
            setVelX(-2);
        }
    }
    
}
