package com.sandersawesomeapps.memorygame.fragments.game;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sandersawesomeapps.memorygame.R;
import com.sandersawesomeapps.memorygame.game.Difficulty;
import com.sandersawesomeapps.memorygame.game.Tile;
import com.sandersawesomeapps.memorygame.game.TileState;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdapter extends ListAdapter<Tile, GridAdapter.TileViewHolder> {

    TileClickListener tileClickListener;

    public GridAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setTileClickListener(TileClickListener tileClickListener) {
        this.tileClickListener = tileClickListener;
    }

    @NonNull
    @Override
    public TileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TileViewHolder(inflater.inflate(R.layout.viewholder_tile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TileViewHolder holder, int position) {
        holder.bind(getItem(position));
        if(this.tileClickListener != null) {
            holder.image.setOnClickListener(view -> tileClickListener.onTileClicked(position));
        }
    }

    public static final DiffUtil.ItemCallback<Tile> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tile>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tile oldItem, @NonNull Tile newItem) {
            boolean result = oldItem.getUnique().equals(newItem.getUnique());
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tile oldItem, @NonNull Tile newItem) {
            boolean result = oldItem.equals(newItem);
            return false;
        }
    };

    static class TileViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tile_image)
        ImageView image;

        @BindView(R.id.tile_text)
        TextView text;

        public TileViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Tile tile) {
            switch (tile.getState()) {
                case HIDDEN: {
                    image.setBackgroundColor(Color.rgb(0, 0, 0));
                    text.setText("");
                    break;
                }
                case SHOWN: {
                    int value = 255 / Difficulty.MEDIUM.getTiles() * tile.getId();
                    image.setBackgroundColor(Color.rgb(value, value, value));
                    text.setText("");
                    break;
                }
                case PLAYER_1: {
                    image.setBackgroundColor(Color.rgb(220, 220, 220));
                    text.setText(text.getContext().getString(R.string.game_player_1));
                    break;
                }
                case PLAYER_2: {
                    image.setBackgroundColor(Color.rgb(220,220,200));
                    text.setText(text.getContext().getString(R.string.game_player_2));
                    break;
                }
            }
        }
    }
}
