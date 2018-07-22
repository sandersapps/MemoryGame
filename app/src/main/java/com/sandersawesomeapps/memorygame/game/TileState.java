package com.sandersawesomeapps.memorygame.game;

public enum TileState {
    /**
     * The tile is face down.
     */
    HIDDEN,
    /**
     * The card is face up.
     */
    SHOWN,
    /**
     * Player 1 has correctly guessed this card.
     */
    PLAYER_1,
    /**
     * Player 2 has correctly guessed this card.
     */
    PLAYER_2
}
