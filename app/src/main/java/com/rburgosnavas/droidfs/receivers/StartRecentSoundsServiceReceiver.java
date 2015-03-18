package com.rburgosnavas.droidfs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rburgosnavas.droidfs.services.RecentSoundsIntentService;

/**
 * This broadcast receiver gets started by ScheduleRecentSoundsReceiver via an alarm service.
 */
public class StartRecentSoundsServiceReceiver extends BroadcastReceiver {
    private static final String TAG = StartRecentSoundsServiceReceiver.class.getSimpleName();

    public StartRecentSoundsServiceReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiving and starting RecentSoundsIntentService");

        // When this broadcast receiver get called, we immediately create an intent for the
        // RecentSoundsIntentService and then start that service.
        Intent recentSoundsService = new Intent(context, RecentSoundsIntentService.class);
        context.startService(recentSoundsService);
    }
}
