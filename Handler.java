import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList; // Use ArrayList instead of LinkedList for potential performance improvement in iteration
import java.util.List;

public class Handler {
    
    // Entities and tiles are now ArrayLists. ArrayLists can have faster iteration times compared to LinkedLists,
    // especially when the size of the list is large and elements are accessed frequently.
    // This change is beneficial if you're primarily iterating over these lists for rendering and updates.
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> tile = new ArrayList<>();

    // Getter methods for entities and tiles. These allow other parts of your program to access
    // and modify these lists if necessary.
    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getTile() {
        return tile;
    }

    public void render(Graphics g) {
        // Renders all entities. This method loops through each entity and calls its render method.
        // If you have a large number of entities, consider implementing spatial partitioning to only render
        // entities that are within or near the visible area, thus reducing the rendering load.
        for (Entity en : entities) {
            en.render(g);
        }

        for (Entity en : tile) {
            en.render(g);
        }
    }

    public void update() {
        for (int i = 0; i< entities.size(); i++) {
            entities.get(i).update();
        }

        for (int i = 0; i< tile.size(); i++) {
            tile.get(i).update();
        }
    }

    // Methods to add or remove entities and tiles. These methods allow dynamic modification of the game world,
    // such as adding or removing characters, obstacles, or environment elements.
    public void addEntity(Entity en) {
        entities.add(en);
    }

    public void addTile(Entity en) {
        tile.add(en);
    }

    public void removeEntity(Entity en) {
        entities.remove(en);
    }
    
    // Finds and returns the player entity from the list of entities.
    // This is useful for operations that need to directly interact with the player,
    // such as camera control or game mechanics that are player-centric.
    public Entity getPlayer() {
        for (Entity en : entities) {
            if (en.getId() == Id.player) {
                return en;
            }
        }
        return null;
    }

    // Creates the initial level layout by adding tiles to represent the ground or other static elements.
    // This method should ideally be called only once or when the level needs to be reset or changed.
    public void createLevel(BufferedImage level) {
        int width = level.getWidth();
        int height = level.getHeight();
        for (int y=0; y<height;y++) {
            for (int x=0; x<width;x++) {
                int pixel = level.getRGB(x, y);

                if (pixel != -1) {
                    if (pixel == -65536) 
                        addEntity(new Player(x*64, y*64, 64, 64, true, Id.player, this));

                    else if (pixel == -16777216) 
                        addTile(new Wall(x*64, y*64, 64, 64, true, Id.wall, this));
                    
                    else if (pixel == -16776961)
                        addEntity(new Mashroom(x*64, y*64, 64, 64, false, Id.mashroom, this));
                }
            }
        }
    }
}
