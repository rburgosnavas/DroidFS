package com.rburgosnavas.droidfs.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Helper class to make Toasts with.
 */
public class Skal {

    /**
     * Creates a Toast.
     *
     * @param context The context.
     * @param isLong Whether the Toast should have a Toast.LENGTH_LONG or Toast.LENGTH_SHORT.
     * @param text The text to display in the Toast.
     */
    public static void make(Context context, boolean isLong, String text) {
        int duration = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;

        Toast.makeText(context, text, duration).show();
    }

    /**
     * Creates a Toast with duration = Toast.LENGTH_LONG.
     *
     * @param context The context.
     * @param text The text to display in the Toast.
     */
    public static void make(Context context, String text) {
        make(context, true, text);
    }
}
