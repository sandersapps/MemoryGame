package com.sandersawesomeapps.memorygame.data;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sandersawesomeapps.memorygame.fragments.TermsFragment;

import androidx.annotation.NonNull;

/**
 * Contains whether the terms have been accepted to determine if the {@link TermsFragment}
 * and the {@link BottomNavigationView} need to be displayed.
 */
public enum TermsState {
    DECLINED(0),
    ACCEPTED(1);

    private int value;

    TermsState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @NonNull
    public static TermsState fromValue(int value) {
        switch (value) {
            case 0: {
                return DECLINED;
            }
            case 1: {
                return ACCEPTED;
            }
            default: {
                return DECLINED;
            }
        }
    }
}
