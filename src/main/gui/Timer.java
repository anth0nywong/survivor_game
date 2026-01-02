package gui;

import gui.GameManager.GameState;

// Timer class to manage time deltas for game updates
public class Timer {
    public static final double FRAME_RATE = 60;
    double delta;
    double enemyDelta;
    double itemDelta;
    long lastTime;

    // EFFECTS: Constructor initializes all deltas to zero
    public Timer() {
        this.delta = 0;
        this.enemyDelta = 0;
        this.itemDelta = 0;
        lastTime = System.nanoTime();
    }

    // EFFECTS: Returns the main delta
    public double getDelta() {
        return delta;
    }

    // MODIFIES: this
    // EFFECTS: Sets the main delta
    public void setDelta(double delta) {
        this.delta = delta;
    }

    // MODIFIES: this
    // EFFECTS: Reduces the main delta by 1
    public void reduceDelta() {
        this.delta--;
    }

    // EFFECTS: Returns the enemy delta
    public double getEnemyDelta() {
        return enemyDelta;
    }

    // MODIFIES: this
    // EFFECTS: Reduces the enemy delta by 1
    public void reduceEnemyDelta() {
        this.enemyDelta--;
    }

    // MODIFIES: this
    public void setEnemyDelta(double enemyDelta) {
        this.enemyDelta = enemyDelta;
    }

    // EFFECTS: Returns the item delta
    public double getItemDelta() {
        return itemDelta;
    }

    // MODIFIES: this
    // EFFECTS: Sets the item delta
    public void setItemDelta(double itemDelta) {
        this.itemDelta = itemDelta;
    }

    // MODIFIES: this
    // EFFECTS: Updates the deltas based on elapsed time and frame rate 
    public void updateDeltas() {
        long now = System.nanoTime();
        double nsPerTick = 1000000000.0 / FRAME_RATE; // 60 updates per second
        delta += (now - lastTime) / nsPerTick;
        enemyDelta += (now - lastTime) / nsPerTick;
        itemDelta += (now - lastTime) / nsPerTick;
        lastTime = now;
    }

    // MODIFIES: this
    // EFFECTS: Updates the last time to current time
    public void updateLastTime() {
        lastTime = System.nanoTime();
    }

    // MODIFIES: this
    // EFFECTS: Updates time based on current game state
    void updateTime(GameState currentState) {
        if (currentState == GameState.PLAYING) {
            updateDeltas();
        } else {
            updateLastTime();
        }
    }
}
