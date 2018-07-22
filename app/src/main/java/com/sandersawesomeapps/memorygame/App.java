package com.sandersawesomeapps.memorygame;

import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;
import com.sandersawesomeapps.memorygame.util.ExceptionHandling;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // Let the Exception Handling class determine what to do with uncaught Exceptions
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                ExceptionHandling.reportException(throwable);
            }
        });

        // setup EasyPrefs
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
