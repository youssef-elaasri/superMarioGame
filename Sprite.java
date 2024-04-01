import java.awt.image.BufferedImage;

public class Sprite {

    private SpriteSheet sheet;
    private BufferedImage image;

    public Sprite(SpriteSheet sheet, int x, int y) {
        image = sheet.getSprite(x, y);
    }

    public BufferedImage getBufferedImage() {
        return image;
    }
}
