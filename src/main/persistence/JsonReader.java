// Citation: This class is based on the sample persistence code provided in the
// CPSC 121 (University of British Columbia) Json Serialization Demo.
// Modifications have been made to fit the structure and requirements
// of my own application.

package persistence;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Enemy;
import model.Item;
import model.Player;
import model.Weapon;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads json object from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JSONObject read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject;
    }

    // REQUIRES: source file exists and is readable
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: reads player data from JSON file and returns a Player object
    // throws IOException if an error occurs reading data from file
    public Player readPlayer() throws IOException {
        JSONObject jsonObject = read();
        Player player = parsePlayer(jsonObject.getJSONObject("player"));
        return player;
    }

    // EFFECTS: reads enemy data from JSON file and returns a list of Enemy objects
    // throws IOException if an error occurs reading data from file
    public ArrayList<Enemy> readEnemies() throws IOException {
        ArrayList<Enemy> list = new ArrayList<Enemy>();
        JSONObject jsonObject = read();
        JSONArray enemiesArray = jsonObject.getJSONArray("enemies");
        for (Object json : enemiesArray) {
            JSONObject nextEnemy = (JSONObject) json;
            Enemy e = parseEnemy(nextEnemy);
            list.add(e);
        }
        return list;
    }

    // EFFECTS: reads item data from JSON file and returns a list of Item objects
    // throws IOException if an error occurs reading data from file
    public ArrayList<Item> readItems() throws IOException {
        ArrayList<Item> list = new ArrayList<Item>();
        JSONObject jsonObject = read();
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        for (Object json : itemsArray) {
            JSONObject nextItem = (JSONObject) json;
            Item item = parseItem(nextItem);
            list.add(item);
        }
        return list;
    }
    
    // EFFECTS: parse the round passed from json and return its value
    // throws IOException if an error occurs reading data from file
    public int readGame() throws IOException {
        JSONObject jsonObject = read();
        int round = jsonObject.getInt("round");
        return round;
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        int level = jsonObject.getInt("level");
        int health = jsonObject.getInt("health");
        int experience = jsonObject.getInt("experience");
        int speed = jsonObject.getInt("speed");
        Player player = new Player();
        player.setLevel(level);
        player.setHealth(health);
        player.setExperience(experience);
        player.setSpeed(speed);
        player.setPosX(posX);
        player.setPosY(posY);
        JSONArray weaponsArray = jsonObject.getJSONArray("weapons");
        readWeapons(player, weaponsArray);
        return player;
    }

    private void readWeapons(Player player, JSONArray weaponsArray) {
        for (Object json : weaponsArray) {
            JSONObject nextWeapon = (JSONObject) json;
            Color color = new Color(nextWeapon.getInt("color"));
            int frequency = nextWeapon.getInt("frequency");
            int bulletNumber = nextWeapon.getInt("bulletNumber");
            int range = nextWeapon.getInt("range");
            int damage = nextWeapon.getInt("damage");
            Weapon weapon = new Weapon(damage, range, color, frequency, bulletNumber);
            player.addWeapon(weapon);
        }
    }

    // EFFECTS: parses enemy from JSON object and returns it
    private Enemy parseEnemy(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        int damage = jsonObject.getInt("damage");
        int health = jsonObject.getInt("health");
        int maxHealth = jsonObject.getInt("maxHealth");
        int range = jsonObject.getInt("range");
        int speed = jsonObject.getInt("speed");
        boolean isDeath = jsonObject.getBoolean("isDeath");
        String icon = jsonObject.getString("icon");
        Color color = new Color(jsonObject.getInt("color"));

        Enemy enemy = new Enemy(posX, posY, speed, maxHealth, health, damage, range, icon, color);
        enemy.setDeath(isDeath);
        return enemy;
    }
    
    // EFFECTS: parses an item from json and return item
    private Item parseItem(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        int health = jsonObject.getInt("health");
        boolean isCollected = jsonObject.getBoolean("isCollected");

        JSONObject weaponObj = jsonObject.getJSONObject("weapon");
        Color color = new Color(weaponObj.getInt("color"));
        int frequency = weaponObj.getInt("frequency");
        int bulletNumber = weaponObj.getInt("bulletNumber");
        int range = weaponObj.getInt("range");
        int damage = weaponObj.getInt("damage");
        Weapon weapon = new Weapon(damage, range, color, frequency, bulletNumber);

        Item item = new Item(posX, posY);
        item.setHealth(health);
        item.setWeapon(weapon);
        item.setCollected(isCollected);

        return item;
    }
}
