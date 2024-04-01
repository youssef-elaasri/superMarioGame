import java.awt.Graphics;

public class Wall extends Entity {

    public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.getBrick().getBufferedImage(), x, y, width,height,null);
    }

    @Override
    public void update() {
        
    }
    
}
