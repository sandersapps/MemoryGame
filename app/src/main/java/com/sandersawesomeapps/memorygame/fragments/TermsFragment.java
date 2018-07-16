package com.sandersawesomeapps.memorygame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandersawesomeapps.memorygame.R;
import com.sandersawesomeapps.memorygame.data.TermsState;
import com.sandersawesomeapps.memorygame.data.TermsStateRepository;
import com.sandersawesomeapps.memorygame.viewmodels.TermsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TermsFragment extends Fragment {

    private Unbinder unbinder;

    private TermsViewModel termsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TermsViewModel.Factory factory = new TermsViewModel.Factory(TermsStateRepository.getInstance());
        termsViewModel = ViewModelProviders.of(this, factory).get(TermsViewModel.class);
    }

    @OnClick(R.id.agree_button)
    void agreeButton() {
        termsViewModel.setTermsState(TermsState.ACCEPTED);
        NavHostFragment.findNavController(this).popBackStack();
    }
}