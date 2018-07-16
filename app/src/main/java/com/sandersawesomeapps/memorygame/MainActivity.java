package com.sandersawesomeapps.memorygame;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sandersawesomeapps.memorygame.data.TermsState;
import com.sandersawesomeapps.memorygame.data.TermsStateRepository;
import com.sandersawesomeapps.memorygame.viewmodels.TermsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNavigationView;

    private TermsViewModel termsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NavigationUI.setupWithNavController(bottomNavigationView, Navigation.findNavController(this, R.id.nav_host));
        TermsViewModel.Factory factory = new TermsViewModel.Factory(TermsStateRepository.getInstance());
        termsViewModel = ViewModelProviders.of(this, factory).get(TermsViewModel.class);
        termsViewModel.getTermsState().observe(this, this::termsChanged);
    }

    private void termsChanged(TermsState state) {
        switch (state) {
            case ACCEPTED: {
                bottomNavigationView.setVisibility(View.VISIBLE);
                break;
            }
            case DECLINED: {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host).navigateUp();
    }
}
