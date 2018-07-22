package com.sandersawesomeapps.memorygame.game;

import com.sandersawesomeapps.memorygame.R;

import androidx.annotation.StringRes;

/**
 * Determines the current state of the {@link Game}.
 */
public enum State {
    /**
     * Indicates that there is no current game and a new one can be started.
     */
    START(R.string.state_start),
    /**
     * Player 1 can make his first pick.
     */
    PLAYER_1_FIRST(R.string.state_player_1_first),
    /**
     * Player 1 can make his second pick.
     */
    PLAYER_1_SECOND(R.string.state_player_1_second),
    /**
     * Player 1 has picked 2 cards and his turn can be ended.
     */
    PLAYER_1_END(R.string.state_player_1_end),
    /**
     * Player 2 can make his first pick.
     */
    PLAYER_2_FIRST(R.string.state_player_2_first),
    /**
     * Player 2 can make his second pick
     */
    PLAYER_2_SECOND(R.string.state_player_2_second),
    /**
     * Player 2 has picked 2 cards and his turn can be ended.
     */
    PLAYER_2_END(R.string.state_player_2_end),
    /**
     * Player 1 has won and the game has ended.
     */
    PLAYER_1_WON(R.string.state_player_1_won),
    /**
     * Player 2 has won and the game has ended.
     */
    PLAYER_2_WON(R.string.state_player_2_won);

    /**
     * The StringRes which describes the current state of the game.
     */
    @StringRes
    private int stringResourceId;

    State(@StringRes int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    @StringRes
    public int getStringResourceId() {
        return this.stringResourceId;
    }
}
