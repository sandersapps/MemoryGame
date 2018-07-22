package com.sandersawesomeapps.memorygame.data;

import com.sandersawesomeapps.memorygame.game.Game;

/**
 * A singleton Repository to make sure there is only one instance of {@link Game} in use
 * by the app.
 */
public class GameRepository {

    // Todo store and retrieve the game in persistant storage to enable resuming after killing the app.

    private static GameRepository instance;

    /**
     * The current {@link Game}.
     */
    private Game game;

    private GameRepository() {
        game = new Game();
    }

    /**
     * Returns the current {@link Game}.
     * @return The current {@link Game}
     */
    public Game getGame() {
        return this.game;
    }

    public static GameRepository getInstance() {
        if(instance == null) {
            synchronized (GameRepository.class) {
                if(instance == null) {
                    instance = new GameRepository();
                }
            }
        }
        return instance;
    }
}
