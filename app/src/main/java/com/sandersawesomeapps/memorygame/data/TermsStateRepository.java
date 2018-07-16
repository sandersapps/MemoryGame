package com.sandersawesomeapps.memorygame.data;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * A singleton Repository to make sure there is only one instance of {@link TermsState} in use
 * by the app which is exposed as {@link LiveData}.
 */
public class TermsStateRepository {

    private static TermsStateRepository instance;

    /**
     * The key at which the current state of {@link TermsState} is stored in the
     * SharedPrefences.
     */
    private static final String STORED_TERMS_STATE = "terms_state";

    /**
     * The current state of the {@link TermsState}.
     */
    private MutableLiveData<TermsState> termsState;

    private TermsStateRepository() {
        termsState = new MutableLiveData<>();
        termsState.setValue(TermsState.fromValue(
                Prefs.getInt(STORED_TERMS_STATE, TermsState.DECLINED.getValue())
        ));
    }

    /**
     * Exposes the current state of {@link TermsState} as {@link LiveData} so the rest of the app
     * can observe wether the terms have been accepted.
     * @return The {@link TermsState} as {@link LiveData}
     */
    public LiveData<TermsState> getTermsState() {
        return termsState;
    }

    /**
     * Updates the current state of the {@link TermsState} and notifies any observers of the
     * current state.
     * @param state
     */
    public void setTermsState(TermsState state) {
        termsState.setValue(state);
        Prefs.putInt(STORED_TERMS_STATE, state.getValue());
    }

    public static TermsStateRepository getInstance() {
        if(instance == null) {
            synchronized (TermsStateRepository.class) {
                if(instance == null) {
                    instance = new TermsStateRepository();
                }
            }
        }
        return instance;
    }
}
