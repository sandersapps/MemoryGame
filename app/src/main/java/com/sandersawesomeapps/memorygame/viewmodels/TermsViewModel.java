package com.sandersawesomeapps.memorygame.viewmodels;

import com.sandersawesomeapps.memorygame.data.TermsState;
import com.sandersawesomeapps.memorygame.data.TermsStateRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TermsViewModel extends ViewModel {

    private TermsStateRepository termsStateRepository;

    private TermsViewModel(TermsStateRepository termsStateRepository) {
        this.termsStateRepository = termsStateRepository;
    }

    public LiveData<TermsState> getTermsState() {
        return this.termsStateRepository.getTermsState();
    }

    public void setTermsState(TermsState state) {
        this.termsStateRepository.setTermsState(state);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private TermsStateRepository termsStateRepository;

        public Factory(TermsStateRepository termsStateRepository) {
            this.termsStateRepository = termsStateRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TermsViewModel(termsStateRepository);
        }
    }
}
