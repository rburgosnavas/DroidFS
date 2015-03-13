package com.rburgosnavas.droidfs.utils;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by rburgosnavas on 12/9/14.
 */
public class AuthUtils {
    private static final String TAG = AuthUtils.class.getSimpleName();

    public static boolean isExpired(long expiration) {
        Calendar c = Calendar.getInstance();

        Log.i(TAG, "\n\nc.getTimeInMillis() = " + c.getTimeInMillis() +
                "\nexpiration = " + expiration);
        Log.i(TAG, "expired? " + (c.getTimeInMillis() >= expiration) + "\n");

        return c.getTimeInMillis() >= expiration;
    }
}
