package com.rburgosnavas.droidfs.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * This is initiated when the application starts.
 */
public class ScheduleRecentSoundsReceiver extends BroadcastReceiver {
    private static final String TAG = ScheduleRecentSoundsReceiver.class.getSimpleName();

    private static final int REQUEST_CODE = 606;
    private static final long INTERVAL = 30 * 60 * 1000;

    public ScheduleRecentSoundsReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiving");

        // Start scheduling
        runAlarm(context);
    }

    public static void runAlarm(Context context) {
        Log.i(TAG, "scheduling alarm");

        // Set the intent that will start the next broadcast receiver in the chain.
        Intent intent = new Intent(context, StartRecentSoundsServiceReceiver.class);

        // Initiate the pending intent to be added to the alarm manager.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // Now!
        Calendar cal = Calendar.getInstance();

        // Get the alarm manager service.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm so that it starts immediately.
        // Every 30 minutes it will trigger StartRecentSoundsServiceReceiver which will start the
        // RecentSoundsIntentService.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), INTERVAL,
                pendingIntent);
    }
}
