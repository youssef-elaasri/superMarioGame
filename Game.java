import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game extends Canvas implements Runnable {

    // Constants for the game window size and scale
    private static final int WIDTH = 270;
    private static final int HEIGHT = WIDTH / 14 * 10;
    private static final int SCALE = 4;
    private static final String TITLE = "Mario";

    // Game thread and running state
    private Thread thread;
    private boolean running = false;
    private static Handler handler;
    private static SpriteSheet sheet;
    private static Sprite brick;
    private static Sprite player[] = new Sprite[4];
    private static Camera camera;
    private BufferedImage image;
    public static Sprite mashroom;


    public Game() {
        // Set the preferred, maximum, and minimum sizes of the game canvas
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        camera = new Camera();
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getSCALE() {
        return SCALE;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Sprite getBrick() {
        return brick;
    }

    public static Sprite[] getPlayer() {
        return player;
    }

    public static Sprite getMashroom() {
        return mashroom;
    }

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("/spritesheet.png");
        addKeyListener(new keyInput());
        brick = new Sprite(sheet, 2, 1);
        mashroom = new Sprite(sheet, 1, 1);
        for (int i=0;i<4; i++) {
            player[i] = new Sprite(sheet, i+1, 2);
        }
        try {
            image = ImageIO.read(getClass().getResource("/level1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler.createLevel(image);
    }

    // Synchronized method to start the game thread
    private synchronized void start() {
        if (running) return; // Do nothing if the game is already running
        running = true;
        thread = new Thread(this, "Display");
        thread.start(); // Start the game thread
    }

    // Synchronized method to stop the game thread
    private synchronized void stop() {
        if (!running) return; // Do nothing if the game is not running
        running = false;
        try {
            thread.join(); // Wait for the game thread to terminate
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // The main game loop
    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; // Target time per tick (60 ticks per second)
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        createBufferStrategy(3); // Create a triple buffer strategy for smooth rendering
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            boolean shouldRender = false;
            while (delta >= 1) {
                update(); // Update game logic
                delta--;
                shouldRender = true;
            }
            if (shouldRender) {
                render(); // Render the game to the screen
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
            try {
                Thread.sleep(2); // Sleep to limit the frame rate
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop(); // Stop the game loop
    }

    // Render the game graphics
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        // Clear the screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.translate(camera.getX(), camera.getY());
        handler.render(g);
        g.dispose();
        bs.show(); // Show the buffer
    }

    // Update the game logic
    public void update() {
        handler.update();
        for (Entity en : handler.getEntities()) {
            if (en.getId()== Id.player) 
                camera.update(en);
        }
    }

    public int getFrameWidth() {
        return WIDTH*SCALE;
    }

    public int getFrameHeight() {
        return HEIGHT*SCALE;
    }

    // Main method to start the game
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack(); // Fit the frame size to the game canvas size
        frame.setResizable(false); // Make the game window non-resizable
        frame.setLocationRelativeTo(null); // Center the game window
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true); // Make the game window visible
        game.start(); // Start the game
    }
}
