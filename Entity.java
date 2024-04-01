import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
    
    protected int x,y;
    protected int width, height;
    private boolean solid;
    protected int velX, velY;
    private Id id;
    protected Handler handler;
    protected boolean jumping = false;
    protected boolean falling = true;
    protected double gravity = 0.0;
    protected int facing = 0; // 0 : left, 1: right

    public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
    }

    public Id getId() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract void render(Graphics g);

    public abstract void update();

    public void die() {
        handler.removeEntity(this);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(x+10,y,width-20,5);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(x+10,y+height-5,width-20,5);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(x,y+10,5,height-20);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(x+width-5,y+10,5,height-20);
    }
}
