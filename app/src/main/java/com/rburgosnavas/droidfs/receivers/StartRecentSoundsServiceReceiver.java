package com.rburgosnavas.droidfs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rburgosnavas.droidfs.services.RecentSoundsService;

/**
 * This broadcast receiver gets started by ScheduledRecentSoundsReceiver via an alarm service.
 */
public class StartRecentSoundsServiceReceiver extends BroadcastReceiver {
    private static final String TAG = StartRecentSoundsServiceReceiver.class.getSimpleName();

    public StartRecentSoundsServiceReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiving and starting RecentSoundsService");

        // When this broadcast receiver get called, we immediately create an intent for the
        // RecentSoundsService and then start that service.
        Intent recentSoundsService = new Intent(context, RecentSoundsService.class);
        context.startService(recentSoundsService);
    }
}
