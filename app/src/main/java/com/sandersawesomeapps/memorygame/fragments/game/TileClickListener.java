package com.sandersawesomeapps.memorygame.fragments.game;

import com.sandersawesomeapps.memorygame.fragments.GameFragment;

/**
 * ClickListener to pass clicks on the {@link GridAdapter.TileViewHolder} to the {@link GameFragment}.
 */
public interface TileClickListener {

    void onTileClicked(int position);
}
