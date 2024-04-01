public class Camera {
    private int x,y;

    public void update(Entity player) {
        setX(-player.getX()+ Game.getHEIGHT()*2);
        setY(-player.getY()+Game.getHEIGHT()*2);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
