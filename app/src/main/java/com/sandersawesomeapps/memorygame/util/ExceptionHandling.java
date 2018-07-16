package com.sandersawesomeapps.memorygame.util;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.sandersawesomeapps.memorygame.BuildConfig;
import com.sandersawesomeapps.memorygame.fragments.TermsFragment;

import androidx.annotation.NonNull;
import io.fabric.sdk.android.Fabric;

public class ExceptionHandling {

    /**
     * Enables Crash Reporting if it's appropriate for the build type. Crash Reporting is disabled
     * by default in the android manifest until the terms have been accepted in the {@link TermsFragment}.
     * @param context any Context.
     */
    public static void enableCrashReporting(@NonNull Context context) {
        if(BuildConfig.ENABLE_CRASH_REPORTING) {
            final Fabric fabric = new Fabric.Builder(context.getApplicationContext())
                    .kits(new Crashlytics())
                    .build();
            Fabric.with(fabric);
        }
    }

    /**
     * Report an Exception to the Crash Reporting if it's appropriate for the build type, prints
     * the stacktrace otherwise.
     * @param e Exception that needs to be logged or printed.
     */
    public static void reportException(@NonNull Throwable e) {
        if(BuildConfig.ENABLE_CRASH_REPORTING) {
            Crashlytics.logException(e);
        } else {
            e.printStackTrace();
        }
    }
}
