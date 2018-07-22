package com.sandersawesomeapps.memorygame.game;

import java.util.Objects;
import java.util.UUID;

public class Tile {

    private String unique;

    private int id;

    private String url;

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
