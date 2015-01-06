package com.rburgosnavas.droidfs.utils;

import java.util.Calendar;

/**
 * Created by rburgosnavas on 12/9/14.
 */
public class AuthUtils {
    public static boolean isExpired(long expiration) {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis() >= expiration;
    }
}
