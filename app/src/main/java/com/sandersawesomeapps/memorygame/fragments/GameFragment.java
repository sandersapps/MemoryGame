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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * {@link Fragment} which displays the actual game.
 */
public class GameFragment extends BaseFragment implements TileClickListener {

    private Unbinder unbinder;

    private GameViewModel viewModel;

    /**
     * Button which is used to go to the next state of the game.
     */
    @BindView(R.id.game_button)
    MaterialButton gameButton;

    /**
     * TextView which is used to display the current state of the game.
     */
    @BindView(R.id.game_state)
    TextView gameStateText;

    /**
     * Contains all the {@link Tile} of the game.
     */
    @BindView(R.id.grid)
    RecyclerView grid;

    /**
     * Adapter to display all the {@link Tile} in the {@link #grid}.
     */
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

                    //todo when scores are implemented, save score and go to highscores fragment

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
        adapter = new GridAdapter(this);
        viewModel.getTiles().observe(this, this::bindList);
        grid.setAdapter(adapter);
        return view;
    }

    /**
     * Bind the updated list to the UI.
     * @param list
     */
    public void bindList(ArrayList<Tile> list) {
        if(list != null) {
            adapter.submitList(list);
            // todo figure out why I have to call notifyDataSetChanged(), according to the documentation submitList() should update the changes. Works for now but it's not clean.
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * A {@link Tile} has been clicked in the {@link #adapter}.
     * @param position
     */
    @Override
    public void onTileClicked(int position) {
        viewModel.tileClick(position);
    }
}
