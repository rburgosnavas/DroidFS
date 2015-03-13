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
public class ScheduledRecentSoundsReceiver extends BroadcastReceiver {
    private static final String TAG = ScheduledRecentSoundsReceiver.class.getSimpleName();

    public ScheduledRecentSoundsReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiving");

        // Start scheduling
        scheduleAlarm(context);
    }

    public static void scheduleAlarm(Context context) {
        Log.i(TAG, "scheduling alarm");

        // Get the alarm manager service.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the intent that will start the next broadcast receiver in the chain.
        Intent intent = new Intent(context, StartRecentSoundsServiceReceiver.class);

        // Initiate the pending intent to be added to the alarm manager.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 606, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // One minute.
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 1);

        // Set the alarm so that it starts in 1 minute and triggers every 30 minutes.
        // So, every 30 minutes it will hit StartRecentSoundsServiceReceiver which will start the
        // RecentSoundsService.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(),
                30 * 60 * 1000,
                // AlarmManager.INTERVAL_HALF_HOUR,
                pendingIntent);
    }
}
