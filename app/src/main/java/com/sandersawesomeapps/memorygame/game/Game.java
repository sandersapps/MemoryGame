package com.sandersawesomeapps.memorygame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * This is the actual game. The game uses {@link #state} to determine what state the game is currently
 * in. To keep the game in a valid state all functions which alter the state are synchronized. If
 * the game somehow ends up in an illegal state it will throw an IllegalStateException.
 *
 * The cards of the memory game are stored in {@link #tiles}. Each {@link Tile} has a state of it's
 * own.
 */
public class Game {

    /**
     * The current State of the game.
     */
    private MutableLiveData<State> state;

    /**
     * The tiles of the game.
     */
    private MutableLiveData<ArrayList<Tile>> tiles;

    /**
     * The current difficulty.
     */
    private Difficulty difficulty;

    /**
     * Remembers the position of the first pick of the current turn. Returns a value of -1 if no
     * tile has been picked yet.
     */
    private int firstTile;

    /**
     * Remembers the position of the second pick of the current turn. Returns a value of -1 if no
     * tile has been picked yet.
     */
    private int secondTile;

    /**
     * Constructor to create a new empty Game.
     */
    public Game() {
        firstTile = -1;
        secondTile = -1;
        state = new MutableLiveData<>();
        state.setValue(State.START);
        tiles = new MutableLiveData<>();
    }

    /**
     * Returs the current state of the game.
     * @return
     */
    public LiveData<State> getGameState() {
        return state;
    }

    /**
     * Returns the current tiles of the game.
     * @return
     */
    public LiveData<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    /**
     * Starts a new game for the given difficulty.
     * @param difficulty
     */
    public synchronized void startNewGame(Difficulty difficulty) {
        this.difficulty = difficulty;

        // create new tiles and shuffle them
        ArrayList<Tile> newTiles = new ArrayList<>();
        for(int i = 0; i < (difficulty.getTiles() / 2); i++) {
            Tile tile1 = new Tile(i, String.valueOf(i), TileState.HIDDEN);
            Tile tile2 = new Tile(i, String.valueOf(i), TileState.HIDDEN);
            newTiles.add(tile1);
            newTiles.add(tile2);
        }
        Collections.shuffle(newTiles);
        tiles.setValue(newTiles);
        Random random = new Random();
        this.state.setValue(random.nextBoolean() ? State.PLAYER_2_FIRST : State.PLAYER_1_FIRST);
    }

    public synchronized void tileClick(int position) throws IllegalStateException {
        if(state.getValue() == null || tiles.getValue() == null) {
            throw new IllegalStateException("Game is in incorrect state");
        }
        ArrayList<Tile> newTiles = tiles.getValue();
        Tile clickedTile = newTiles.get(position);
        if(clickedTile == null) {
            throw new IllegalStateException("Tile for position " + String.valueOf(position) +
                    " is null");
        }
        if(clickedTile.getState() == TileState.HIDDEN) {
            switch (state.getValue()) {
                case PLAYER_1_FIRST: {
                    firstTile = position;
                    clickedTile.setState(TileState.SHOWN);
                    newTiles.set(position, clickedTile);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_1_SECOND);
                    break;
                }
                case PLAYER_1_SECOND: {
                    secondTile = position;
                    clickedTile.setState(TileState.SHOWN);
                    newTiles.set(position, clickedTile);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_1_END);
                    break;
                }
                case PLAYER_2_FIRST: {
                    firstTile = position;
                    clickedTile.setState(TileState.SHOWN);
                    newTiles.set(position, clickedTile);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_2_SECOND);
                    break;
                }
                case PLAYER_2_SECOND: {
                    secondTile = position;
                    clickedTile.setState(TileState.SHOWN);
                    newTiles.set(position, clickedTile);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_2_END);
                    break;
                }
            }
        }
    }

    /**
     * Ends the turn after a player has picked 2 tiles. Call this from the UI and not automatically
     * after picking the second Tile to give players some time to look at the second card.
     */
    public synchronized void endTurn() {
        if(state.getValue() == null || tiles.getValue() == null) {
            throw new IllegalStateException("Game is in incorrect state");
        }
        ArrayList<Tile> newTiles = tiles.getValue();
        Tile tile1 = newTiles.get(firstTile);
        Tile tile2 = newTiles.get(secondTile);
        if(tile1 == null || tile2 == null) {
            throw new IllegalStateException("Tile was null");
        }
        switch (state.getValue()) {
            case PLAYER_1_END: {
                if(tile1.getId() != tile2.getId()) {
                    tile1.setState(TileState.HIDDEN);
                    tile2.setState(TileState.HIDDEN);
                    newTiles.set(firstTile, tile1);
                    newTiles.set(secondTile, tile2);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_2_FIRST);
                } else {
                    tile1.setState(TileState.PLAYER_1);
                    tile2.setState(TileState.PLAYER_1);
                    newTiles.set(firstTile, tile1);
                    newTiles.set(secondTile, tile2);
                    tiles.setValue(newTiles);
                    boolean finished = true;
                    for(Tile tile : newTiles) {
                        if(tile != null && tile.getState() == TileState.HIDDEN) {
                            finished = false;
                            break;
                        }
                    }
                    if(!finished) {
                        state.setValue(State.PLAYER_1_FIRST);
                    } else {
                        finishGame();
                    }
                }
                break;
            }
            case PLAYER_2_END: {
                if(tile1.getId() != tile2.getId()) {
                    tile1.setState(TileState.HIDDEN);
                    tile2.setState(TileState.HIDDEN);
                    newTiles.set(firstTile, tile1);
                    newTiles.set(secondTile, tile2);
                    tiles.setValue(newTiles);
                    state.setValue(State.PLAYER_1_FIRST);
                } else {
                    tile1.setState(TileState.PLAYER_2);
                    tile2.setState(TileState.PLAYER_2);
                    newTiles.set(firstTile, tile1);
                    newTiles.set(secondTile, tile2);
                    tiles.setValue(newTiles);
                    boolean finished = true;
                    for(Tile tile : newTiles) {
                        if(tile != null && tile.getState() == TileState.HIDDEN) {
                            finished = false;
                            break;
                        }
                    }
                    if(!finished) {
                        state.setValue(State.PLAYER_2_FIRST);
                    } else {
                        finishGame();
                    }
                }
                break;
            }
        }
        firstTile = -1;
        secondTile = -1;
    }

    /**
     * Finishes the game after all tiles have been reveiled.
     * @throws IllegalStateException
     */
    private synchronized void finishGame() throws IllegalStateException {
        if(tiles.getValue() == null) {
            throw new IllegalStateException("Game is in incorrect state");
        }
        int player1Score = 0;
        int player2Score = 0;
        for(Tile tile : tiles.getValue()) {
            if(tile.getState() == null) {
                throw new IllegalStateException("Tile doesn't have a state");
            }
            switch (tile.getState()) {
                case PLAYER_1: {
                    player1Score++;
                    break;
                }
                case PLAYER_2: {
                    player2Score++;
                    break;
                }
                default: {
                    throw new IllegalStateException("Tile didn't have a player state");
                }
            }
        }
        if(player1Score > player2Score) {
            state.setValue(State.PLAYER_1_WON);
        } else {
            state.setValue(State.PLAYER_2_WON);
        }
    }
}