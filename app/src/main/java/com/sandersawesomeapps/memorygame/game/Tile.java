package com.sandersawesomeapps.memorygame.game;

import com.sandersawesomeapps.memorygame.fragments.game.GridAdapter;

import java.util.Objects;
import java.util.UUID;

/**
 * A tile represents a card. It has a {@link TileState} to track how it should be displayed.
 */
public class Tile {

    /**
     * A unique id which is used for the {@link GridAdapter} to make each Tile unique for ViewHolder
     * purposes.
     */
    private String unique;

    /**
     * The id of the Tile. There's always 2 Tiles with the same id, if the id's match the cards
     * are considered the same for game purposes.
     */
    private int id;

    /**
     * The url of the image.
     */
    private String url;

    /**
     * The current state of the Tile.
     */
    private TileState state;

    public Tile(int id, String url, TileState state) {
        this.unique = UUID.randomUUID().toString();
        this.id = id;
        this.url = url;
        this.state = state;
    }

    public String getUnique() {
        return unique;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return id == tile.id &&
                Objects.equals(url, tile.url) &&
                state == tile.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, state);
    }
}
