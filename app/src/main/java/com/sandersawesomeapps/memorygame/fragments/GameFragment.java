package com.sandersawesomeapps.memorygame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.material.button.MaterialButton;
import com.sandersawesomeapps.memorygame.BaseFragment;
import com.sandersawesomeapps.memorygame.R;
import com.sandersawesomeapps.memorygame.data.GameRepository;
import com.sandersawesomeapps.memorygame.fragments.game.GridAdapter;
import com.sandersawesomeapps.memorygame.fragments.game.TileClickListener;
import com.sandersawesomeapps.memorygame.game.Difficulty;
import com.sandersawesomeapps.memorygame.game.Tile;
import com.sandersawesomeapps.memorygame.viewmodels.GameViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GameFragment extends BaseFragment implements TileClickListener {

    private Unbinder unbinder;

    private GameViewModel viewModel;

    @BindView(R.id.game_button)
    MaterialButton gameButton;

    @BindView(R.id.game_state)
    TextView gameStateText;

    @BindView(R.id.grid)
    RecyclerView grid;

    GridAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameViewModel.Factory factory = new GameViewModel.Factory(GameRepository.getInstance());
        viewModel = ViewModelProviders.of(this, factory).get(GameViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Set ui elements based on state
        viewModel.getState().observe(this, state -> {
            gameStateText.setText(getString(state.getStringResourceId()));
            switch (state) {
                case PLAYER_1_FIRST:
                case PLAYER_1_SECOND:
                case PLAYER_2_FIRST:
                case PLAYER_2_SECOND: {
                    gameButton.setVisibility(View.INVISIBLE);
                    break;
                }
                case PLAYER_1_END:
                case PLAYER_2_END: {
                    gameButton.setVisibility(View.VISIBLE);
                    gameButton.setText(getString(R.string.game_button_next_turn));
                    gameButton.setOnClickListener(buttonView -> viewModel.endTurn());
                    break;
                }
                case PLAYER_1_WON:
                case PLAYER_2_WON: {
                    gameButton.setVisibility(View.VISIBLE);
                    gameButton.setText(getString(R.string.game_button_new));
                    gameButton.setOnClickListener(buttonView -> viewModel.startNewGame(Difficulty.MEDIUM));
                    /*
                    gameButton.setVisibility(View.VISIBLE);
                    gameButton.setText(getString(R.string.game_button_finish));
                    gameButton.setOnClickListener((buttonView -> NavHostFragment.findNavController(this).navigate(R.id.action_game_to_highscores)));
                    */
                    break;
                }
                case START:
                default: {
                    gameButton.setVisibility(View.VISIBLE);
                    gameButton.setText(getString(R.string.game_button_new));
                    gameButton.setOnClickListener(buttonView -> viewModel.startNewGame(Difficulty.MEDIUM));
                    break;
                }
            }
        });

        // Build the game grid
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 4);
        grid.setLayoutManager(layoutManager);
        adapter = new GridAdapter();
        adapter.setTileClickListener(this);
        viewModel.getTiles().observe(this, this::bindList);
        grid.setAdapter(adapter);
        return view;
    }

    public void bindList(ArrayList<Tile> list) {
        if(list != null) {
            adapter.submitList(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTileClicked(int position) {
        viewModel.tileClick(position);
    }
}
