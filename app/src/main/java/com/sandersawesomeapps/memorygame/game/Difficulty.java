package com.sandersawesomeapps.memorygame.game;

/**
 * Determines the difficulty of the game. Higher difficulty will result in a bigger board
 * and a computer opponent will remember more revealed {@link Tile}.
 */
public enum Difficulty {
    EASY(10, 0.5f),
    MEDIUM(14, 0.7f),
    HARD(18, 0.9f),
    IMPOSSIBLE(22, 1f);

    /**
     * The amount of {@link Tile} on the board.
     */
    int tiles;

    /**
     * All {@link Tile} which are revealed will be remembered by a computer opponent. This float
     * represents the chance the computer will remember each {@link Tile}. A higher value gives
     * the computer a better memory.
     */
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
