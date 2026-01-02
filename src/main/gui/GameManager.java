/**
 * Image reference: <a href='https://pngtree.com/freepng/ufo-an-alien-spaceship-isolated-on-a-transparent-background-3d-rendered-illustration_5430148.html'>png image from pngtree.com/</a>
 **/

package gui;

import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.buttons.*;
import gui.controllers.*;
import model.*;
import persistence.*;

public class GameManager extends JPanel implements Runnable, Writable {
    private static final double SCALE = 0.1;
    private Thread gameThread;
    List<GameController> controllers;
    List<Button> buttons;
    
    public enum GameState {
        MENU,
        PLAYING,
        WEAPON,
        LOG
    }
    
    private GameState currentState;
    
    private static final String JSON_STORE = "./data/game_save.json";
    private static final long ENEMY_INTERVAL = 5; // 5 seconds
    private static final int ITEM_INTERVAL = 10; // 10 seconds
    private static final int MAX_ITEM_ON_MAP = 2;
    private ArrayList<GameObject> objs;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private ArrayList<Bullet> bullets;
    
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    
    private boolean running = false;
    private Image playerImage;
    
    // EFFECTS: constructs the real-time game manager with given width and height
    public GameManager(int width, int height) {
        // Try loading a standalone player image `data/ufo.png`. If not present,
        // fall back to loading a soldier sprite sheet and extracting the first frame.
        try {
            File ufoFile = new File("data/ufo.png");
            if (ufoFile.exists()) {
                playerImage = ImageIO.read(ufoFile);
            }
        } catch (IOException e) {
            System.err.println("Warning: player image/sprite not found: " + e.getMessage());
        }
        this.controllers = new ArrayList<GameController>();

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setLayout(null);
        requestFocus();
        
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        currentState = GameState.MENU;
        
        startScreenButtonSetup();

        InterfaceController interfaceController = new InterfaceController(this);
        this.addKeyListener(interfaceController);
        
        startGame(false);
        
    }
    
    // MODIFIES: this
    // EFFECTS: initializes player, enemy list and scanner
    public void startGame(boolean start) {
        objs = new ArrayList<GameObject>();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        items = new ArrayList<Item>();
        player = new Player();

        addToCollisionObjects(player);

        objs.add(player);
        
        if (start) {
            this.setCurrentState(GameState.PLAYING);
        }
    }

    // MODIFIES: this
    // EFFECTS: toggles between playing and menu state when pressing P key
    public void toggleRunning() {
        if (this.currentState == GameState.PLAYING) {
            this.setCurrentState(GameState.MENU);
        } else {
            this.setCurrentState(GameState.PLAYING);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up start screen buttons
    public void startScreenButtonSetup() {
        if (buttons != null) {
            hideMenuButtons();
        }
        buttons = new ArrayList<Button>();
        buttons.add(new StartButton(this));
        buttons.add(new gui.buttons.SaveButton(this));
        buttons.add(new gui.buttons.LoadButton(this));
    }

    // MODIFIES: this
    // EFFECTS: sets up pause screen buttons
    public void pauseScreenButtonSetup() {
        if (buttons != null) {
            hideMenuButtons();
        }
        buttons = new ArrayList<Button>();
        buttons.add(new ContinueButton(this));
        buttons.add(new WeaponSceneButton(this));
        buttons.add(new gui.buttons.SaveButton(this));
        buttons.add(new gui.buttons.LoadButton(this));
        buttons.add(new StartButton(this));
        buttons.add(new LogSceneButton(this));
    }

    // MODIFIES: this
    // EFFECTS: sets up game over screen buttons
    public void gameOverScreenButtonSetup() {
        if (buttons != null) {
            hideMenuButtons();
        }
        buttons = new ArrayList<Button>();
        buttons.add(new RestartButton(this));
        buttons.add(new gui.buttons.LoadButton(this));
        buttons.add(new gui.buttons.SaveButton(this));
    }

    // MODIFIES: this
    // EFFECTS: sets up weapon screen buttons
    public void weaponScreenButtonSetup() {
        if (buttons != null) {
            hideMenuButtons();
        }
        buttons = new ArrayList<Button>();
        buttons.add(new BackToMenuButton(this));
    }

    // MODIFIES: this
    // EFFECTS: sets up log screen buttons
    public void logSceneButtonSetup() {
        if (buttons != null) {
            hideMenuButtons();
        }
        buttons = new ArrayList<Button>();
        buttons.add(new ClearLogButton(this));
        buttons.add(new SaveLogButton(this));
        buttons.add(new BackToMenuButton(this));
    }
    
    // MODIFIES: this
    // EFFECTS: sets the current game state
    public void setCurrentState(GameState nextState) {

        if (currentState == GameState.MENU && nextState != GameState.MENU) {
            hideMenuButtons();
        }
        if (nextState == GameState.MENU) {
            pauseScreenButtonSetup();
            showMenuButtons();
        }
        if (nextState == GameState.PLAYING) {
            applyControllers();
        }
        if (nextState == GameState.WEAPON) {
            weaponScreenButtonSetup();
            showMenuButtons();
        }
        if (nextState == GameState.LOG) {
            logSceneButtonSetup();
            showMenuButtons();
        }


        this.currentState = nextState;
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: applies controllers to all game objects
    void applyControllers() {
        
        this.controllers = new ArrayList<>();

        PlayerController playerController = new PlayerController(player);
        this.addKeyListener(playerController);
        this.controllers.add(playerController);


        // Make sure the panel can receive keyboard focus
        setFocusable(true);
        requestFocusInWindow();
        
        for (Enemy e : this.enemies) {
            this.controllers.add(new EnemyController(e));
        }

        for (Bullet b : bullets) {
            BulletController bc = new BulletController(b);
            this.controllers.add(bc);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: shows menu buttons
    private void showMenuButtons() {
        revalidate();
        repaint();
    }
    
    // MODIFIES: this
    // EFFECTS: hides menu buttons
    private void hideMenuButtons() {
        for (Button button : buttons) {
            remove(button.getButton());
        }
        revalidate();
        repaint();
    }

    // EFFECTS: game loop to run the game, updates and repaints at fixed frame rate
    @Override
    public void run() {

        Timer timer = new Timer();

        while (running) {
            timer.updateTime(this.currentState);
            while (timer.getDelta() >= 1 && this.currentState == GameState.PLAYING) {
                update();
                timer.reduceDelta();
                if (timer.getEnemyDelta() >= ENEMY_INTERVAL * Timer.FRAME_RATE) {
                    this.generateEnemies();
                    timer.setEnemyDelta(0);
                }
                if (timer.getItemDelta() >= ITEM_INTERVAL * Timer.FRAME_RATE && items.size() < MAX_ITEM_ON_MAP) {
                    this.generateItem();
                    timer.setItemDelta(0);
                }
            }
            repaint();
            try {
                Thread.sleep(2); // Prevents CPU overload
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    // MODIFIES: this
    // EFFECTS: updates all game objects
    private void update() {
        for (GameController controller : controllers) {
            controller.update(objs, player);
        }
        if (player.getIsDeadth()) {
            this.setCurrentState(GameState.MENU);
            gameOverScreenButtonSetup();
        }
        for (Weapon w : player.getWeapons()) {
            ArrayList<Bullet> bullets = w.fire(enemies, player);
            for (Bullet b : bullets) {
                BulletController bc = new BulletController(b);
                this.controllers.add(bc);
            }
            this.objs.addAll(bullets);
            this.bullets.addAll(bullets);
        }
        ensureCollisionObjects();
        clearDeadhedObject();
    }
    
    // MODIFIES: this
    // EFFECTS: start game loop thread
    public void start() {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    // Effect: paint component on screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear screen
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (getCurrentState()) {
            case MENU:
                menuScene(g);
                break;
            case PLAYING:
                playgroundScene(g);
                break;
            case WEAPON:
                weaponScene(g);
                break;
            case LOG:
                drawLog(g);
                break;
            default:
                break;
        }
    }
    
    // MODIFIES: g
    // EFFECTS: draws the playground scene
    void playgroundScene(Graphics g) {

        g.setColor(Color.BLACK);
        g.drawString("Press P to pause: " + player.getLevel(), getWidth() - 100, 20);

        // Display player experience bar
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, (int) (player.getExperience() * getWidth() / 100), 10);
        
        // Display player level
        g.setColor(Color.BLACK);
        g.drawString("Player Level: " + player.getLevel(), 20, 20);

        // Draw enemies
        for (Enemy enemy : enemies) {
            this.drawCircle(enemy.getPosX(), enemy.getPosY(), enemy.getSize(), enemy.getColor(), g);
            this.drawHealthBar(g, enemy);
        }
        // Draw player
        if (this.player != null) {
            this.drawPlayer(g);
        }

        // Draw items
        for (Item item : items) {
            if (!item.isCollected()) {
                this.drawCircle(item.getPosX(), item.getPosY(), item.getSize(), item.getColor(), g);
            }
        }

        // Draw bullets
        for (Bullet bullet : bullets) {
            this.drawCircle(bullet.getPosX(), bullet.getPosY(), bullet.getSize(), bullet.getColor(), g);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws the player at its position
    void drawPlayer(Graphics g) {
        int x = player.getPosX();
        int y = player.getPosY();
        int size = player.getSize();   // the GameObject.size is treated as radius elsewhere

        if (playerImage != null) {
            // Draw the standalone image using world->screen SCALE
            int imgX = (int) ((x - size) * SCALE);
            int imgY = (int) ((y - size) * SCALE);
            int imgSize = (int) (size * 2 * SCALE);
            if (imgSize <= 0) {
                imgSize = 1;
            }
            g.drawImage(playerImage, imgX, imgY, imgSize, imgSize, null);
            g.setColor(Color.RED);
            x = (int) ((player.getPosX() - player.getSize()) * SCALE);
            y = (int) ((player.getPosY() - player.getSize()) * SCALE);

            //int diameter = (int) (player.getSize() * 2 * SCALE);
            //g.drawOval(x, y, diameter, diameter); for degbugging
        } else {
            // fallback: draw a circle if no sprite/image available
            this.drawCircle(x, y, size, player.getColor(), g);
        }

        this.drawHealthBar(g, player);
    }
    
    // MODIFIES: g
    // EFFECTS: draws health bar above the game object
    void drawHealthBar(Graphics g, GameObject object) {
        int barWidth = object.getMaxHealth() * 3;
        int barHeight = 25;
        int x = (int) ((object.getPosX() - barWidth / 2) * SCALE);
        int y = (int) ((object.getPosY() - object.getSize() - 100) * SCALE);

        // Draw background
        g.setColor(Color.RED);
        g.fillRect(x, y, (int) (barWidth * SCALE), (int) (barHeight * SCALE));

        // Draw health
        g.setColor(Color.GREEN);
        int healthWidth = (int) ((object.getHealth() / (double) object.getMaxHealth()) * barWidth * SCALE);
        g.fillRect(x, y, healthWidth, (int) (barHeight * SCALE));
    }
    
    // MODIFIES: g
    // EFFECTS: draws the menu scene
    void menuScene(Graphics g) {
        // Draw menu background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw menu options (placeholder)
        g.setColor(Color.WHITE);
        g.drawString("", getWidth() / 2 - 70, getHeight() / 2);

        displayButtons(0);
    }
    
    // MODIFIES: g
    // EFFECTS: draws the weapon scene
    void weaponScene(Graphics g) {
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.BLACK);
        int offset = 0;
        
        offset = displayWeaponList(g, offset);
        displayButtons(offset);

    }

    // MODIFIES: g
    // EFFECTS: displays the player's weapon list with details
    int displayWeaponList(Graphics g, int offset) {
        
        for (Weapon w : player.getWeapons()) {
            Color weaponColor = w.getColor();
            g.setColor(weaponColor);
            int ovalX = getWidth() / 2 - 90;
            int ovalY = getHeight() / 2 - 30 + offset;
            int ovalW = 20;
            int ovalH = 20;
            g.drawOval(ovalX, ovalY, ovalW, ovalH);
            g.fillOval(ovalX, ovalY, ovalW, ovalH);
            g.setColor(Color.BLACK);

            // Compute baseline so text is vertically centered with the oval
            java.awt.FontMetrics fm = g.getFontMetrics();
            int centerY = ovalY + ovalH / 2;
            int baseline = centerY + (fm.getAscent() - fm.getDescent()) / 2;

            int textX = ovalX + ovalW + 10; // place text to the right of the oval
            g.drawString("Damage: " + w.getDamage()
                    + ", Frequency: " + w.getFrequency() + ", Bullets: " + w.getBulletNumber(),
                    textX, baseline);
            offset += 40;
        }
        return offset;
    }

    // MODIFIES: this
    // EFFECTS: displays buttons centered horizontally with given vertical offset
    void displayButtons(int offsetY) {
        // Layout and add buttons once, sized to fit text with padding
        for (int i = 0; i < buttons.size(); i++) {
            JButton jb = buttons.get(i).getButton();
            Dimension pref = jb.getPreferredSize();
            int width = Math.max(100, pref.width + 20); // ensure a minimum width
            int height = Math.max(25, pref.height + 6); // ensure a minimum height with offset
            int x = getWidth() / 2 - width / 2;
            int y = getHeight() / 2 + 40 * i + offsetY;
            jb.setBounds(x, y, width, height);
            if (jb.getParent() != this) {
                this.add(jb);
            }
        }
    }
    
    // MODIFIES: g
    // EFFECTS: draws a circle at given position with given radius and color
    void drawCircle(int posX, int posY, int radius, Color color, Graphics g) {
        g.setColor(color);

        int x = (int) ((posX - radius) * SCALE);
        int y = (int) ((posY - radius) * SCALE);
        int diameter = (int) (radius * 2 * SCALE);

        // Draw filled circle
        g.drawOval(x, y, diameter, diameter);
        g.fillOval(x, y, diameter, diameter);

    }
    
    // MODIFIES: this
    // EFFECTS: generates an enemy at random position not occupied by player or
    // other enemies
    void generateEnemies() {
        int posX = 0;
        int posY = 0;
        do {
            posX = (int) (Math.random() * (getWidth() / SCALE)) + 1;
        } while (Math.abs(posX - player.getPosX()) < 500);

        do {
            posY = (int) (Math.random() * (getHeight() / SCALE)) + 1;
        } while (Math.abs(posY - player.getPosY()) < 500);

        for (int i = 0; i < objs.size(); i++) {
            if (objs.get(i).getPosX() == posX && objs.get(i).getPosY() == posY) {
                generateEnemies();
                return;
            }
        }
        int maxHealth = (int) (Math.random() * 150) + 50;
        Color color = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Enemy newEnemy = new Enemy(posX, posY, 10, maxHealth, maxHealth, 1, 2, "o", color);
        this.addEnemy(newEnemy);
    }

    // MODIFIES: this
    // EFFECT: create a random item containing a weapon on the map
    void generateItem() {
        int posX = 0;
        int posY = 0;
        do {
            posX = (int) (Math.random() * (getWidth() / SCALE)) + 1;
        } while (Math.abs(posX - player.getPosX()) < 5);

        do {
            posY = (int) (Math.random() * (getHeight() / SCALE)) + 1;
        } while (Math.abs(posY - player.getPosY()) < 5);

        for (int i = 0; i < objs.size(); i++) {
            if (objs.get(i).getPosX() == posX && objs.get(i).getPosY() == posY) {
                generateItem();
                return;
            }
        }

        Item item = new Item(posX, posY);
        this.addItem(item);
    }
    
    // EFFECTS: returns the current game state
    public GameState getCurrentState() {
        return currentState;
    }
    
    // EFFECTS: saves the game progress to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved Game to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: adds the given game object to the collision list
    public void addToCollisionObjects(GameObject obj) {
        this.objs.add(obj);
    }

    @Override
    // EFFECTS: returns a JSON object representing the game state
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("player", player.toJson());

        JSONArray enemiesArray = new JSONArray();
        for (Enemy e : enemies) {
            enemiesArray.put(e.toJson());
        }
        json.put("enemies", enemiesArray);

        JSONArray itemsArray = new JSONArray();
        for (Item i : items) {
            itemsArray.put(i.toJson());
        }
        json.put("items", itemsArray);
        return json;
    }
    
    // MODIFIES: this
    // EFFECTS: loads game state from JSON file and restores player, enemies, items,
    //          and collision list
    public void loadGame() {
        try {
            this.objs = new ArrayList<GameObject>();
            this.bullets = new ArrayList<Bullet>();
            startGame(false);
            this.player = jsonReader.readPlayer();
            this.enemies = jsonReader.readEnemies();
            this.items = jsonReader.readItems();
            
            addToCollisionObjects(player);
            
            for (Enemy e : this.enemies) {
                addToCollisionObjects(e);
            }
            for (Item item : this.items) {
                addToCollisionObjects(item);
            }

            System.out.println("Loaded game from " + JSON_STORE);
            this.setCurrentState(GameManager.GameState.PLAYING);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: clears dead or collected objects from the game
    void clearDeadhedObject() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getIsDeadth()) {
                enemies.remove(i);
                objs.remove(i);
            }
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isCollected()) {
                items.remove(i);
                objs.remove(i);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getIsDeadth() || bulletIsOutOfBound(bullets.get(i))) {
                bullets.remove(i);
                objs.remove(i);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: ensures that all game objects are included in the collision list
    void ensureCollisionObjects() {
        if (!objs.contains(player)) {
            objs.add(player);
        }
        for (Enemy e : this.enemies) {
            if (!objs.contains(e)) {
                objs.add(e);
            }
        }
        for (Item item : this.items) {
            if (!objs.contains(item)) {
                objs.add(item);
            }
        }
        for (Bullet b : this.bullets) {
            if (!objs.contains(b)) {
                objs.add(b);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns true if the bullet is out of the game bounds
    boolean bulletIsOutOfBound(Bullet b) {
        if (b.getPosX() < 0 || b.getPosX() > getWidth() / SCALE || b.getPosY() < 0
                || b.getPosY() > getHeight() / SCALE) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds enemy to the game manager and log event
    public void addEnemy(Enemy e) {
        this.enemies.add(e);
        addToCollisionObjects(e);
        this.controllers.add(new EnemyController(e));
    }

    // MODIFIES: this
    // EFFECTS: adds item to the game manager and log event
    public void addItem(Item i) {
        this.items.add(i);
        addToCollisionObjects(i);
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void drawLog(Graphics g) {
        int logX = 10;
        int logY = 30;
        int lineHeight = 15;
        g.setColor(Color.WHITE);
        g.drawString("Event Log:", logX, logY);
        int offsetY = lineHeight;
        for (Event e : EventLog.getInstance()) {
            g.drawString(e.toString(), logX, logY + offsetY);
            offsetY += lineHeight;
        }
        displayButtons(offsetY);
    }
}
