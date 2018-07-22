package com.sandersawesomeapps.memorygame.game;

import com.sandersawesomeapps.memorygame.R;

import androidx.annotation.StringRes;

public enum State {
    START(R.string.state_start),
    PLAYER_1_FIRST(R.string.state_player_1_first),
    PLAYER_1_SECOND(R.string.state_player_1_second),
    PLAYER_1_END(R.string.state_player_1_end),
    PLAYER_2_FIRST(R.string.state_player_2_first),
    PLAYER_2_SECOND(R.string.state_player_2_second),
    PLAYER_2_END(R.string.state_player_2_end),
    PLAYER_1_WON(R.string.state_player_1_won),
    PLAYER_2_WON(R.string.state_player_2_won);

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
