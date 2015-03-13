package com.rburgosnavas.droidfs.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rburgosnavas.droidfs.R;

public class RecentSoundsResultsReceiver extends BroadcastReceiver {
    private static final String TAG = RecentSoundsResultsReceiver.class.getSimpleName();
    private ResultsListener listener;

    public RecentSoundsResultsReceiver() { }

    public void setResultsListener(ResultsListener listener) {
        // Set up the listener here and not in the constructor because a default constructor with
        // no arguments is needed.
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "receiving");
        Log.i(TAG, "calling ResultsListener.onResultsReceive(...)\n");

        // Notify whoever implements ResultsListener that we have data.
        listener.onResultsReceive(context, intent);

        // TODO - re-enable vibration later (make it less annoying).
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("DroidFS - Recent sounds")
                .setContentText("Got new sounds!")
                /*.setVibrate(new long[]{0, 200, 1000})*/;

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(607, builder.build());
    }

    public interface ResultsListener {
        public void onResultsReceive(Context context, Intent intent);
    }
}
