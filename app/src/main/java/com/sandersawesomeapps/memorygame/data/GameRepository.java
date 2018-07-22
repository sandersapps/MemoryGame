package com.sandersawesomeapps.memorygame.data;

import com.sandersawesomeapps.memorygame.game.Game;

public class GameRepository {

    private static GameRepository instance;

    private Game game;

    private GameRepository() {
        game = new Game();
    }

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
