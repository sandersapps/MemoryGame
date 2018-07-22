package com.sandersawesomeapps.memorygame;

import android.os.Bundle;

import com.sandersawesomeapps.memorygame.data.TermsState;
import com.sandersawesomeapps.memorygame.data.TermsStateRepository;
import com.sandersawesomeapps.memorygame.fragments.TermsFragment;
import com.sandersawesomeapps.memorygame.viewmodels.TermsViewModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class BaseFragment extends Fragment {

    private TermsViewModel termsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Redirects the user to the {@link TermsFragment} if the terms have not been accepted.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TermsViewModel.Factory factory = new TermsViewModel.Factory(TermsStateRepository.getInstance());
        termsViewModel = ViewModelProviders.of(this, factory).get(TermsViewModel.class);
        termsViewModel.getTermsState().observe(this, this::termsChanged);
    }

    private void termsChanged(TermsState state) {
        switch (state) {
            case DECLINED: {
                NavHostFragment.findNavController(this).navigate(R.id.termsFragment);
                break;
            }
        }
    }
}