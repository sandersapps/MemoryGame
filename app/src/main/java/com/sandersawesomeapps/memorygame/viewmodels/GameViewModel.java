package com.sandersawesomeapps.memorygame.viewmodels;

import com.sandersawesomeapps.memorygame.data.GameRepository;
import com.sandersawesomeapps.memorygame.game.Difficulty;
import com.sandersawesomeapps.memorygame.game.State;
import com.sandersawesomeapps.memorygame.game.Tile;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GameViewModel extends ViewModel {

    private GameRepository gameRepository;

    private GameViewModel(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public LiveData<State> getState() {
        return this.gameRepository.getGame().getGameState();
    }

    public LiveData<ArrayList<Tile>> getTiles() {
        return this.gameRepository.getGame().getTiles();
    }

    public void startNewGame(Difficulty difficulty) {
        this.gameRepository.getGame().startNewGame(difficulty);
    }

    public void tileClick(int position) {
        this.gameRepository.getGame().tileClick(position);
    }

    public void endTurn() {
        this.gameRepository.getGame().endTurn();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private GameRepository gameRepository;

        public Factory(GameRepository gameRepository) {
            this.gameRepository = gameRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            // noinspection unchecked
            return (T) new GameViewModel(gameRepository);
        }
    }
}