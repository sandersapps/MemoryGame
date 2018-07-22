package com.sandersawesomeapps.memorygame.game;

public enum Difficulty {
    EASY(10, 0.5f),
    MEDIUM(14, 0.7f),
    HARD(18, 0.9f),
    IMPOSSIBLE(22, 1f);

    int tiles;

    float memory;

    Difficulty(int tiles, float memory) {
        this.tiles = tiles;
        this.memory = memory;
    }

    public int getTiles() {
        return this.tiles;
    }

    public float getMemory() {
        return this.memory;
    }
}
