package com.rburgosnavas.droidfs.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.rburgosnavas.droidfs.R;
import com.rburgosnavas.droidfs.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.httpservices.FreesoundApiService;
import com.rburgosnavas.droidfs.models.Result;
import com.rburgosnavas.droidfs.models.Sound;
import com.rburgosnavas.droidfs.utils.AuthUtils;

/**
 *
 */
public class RecentSoundsIntentService extends IntentService {
    private static final String TAG = RecentSoundsIntentService.class.getSimpleName();

    public RecentSoundsIntentService() {
        super("RecentSoundsIntentService");
        Log.i(TAG, "service started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "getting recent sounds");

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        Log.i(TAG, "Current network info: " + ni);

        boolean isConnected = ni != null && ni.isConnected();

        if (!isConnected) {
            Log.e(TAG, "Network not connected.\nCould not retrieve sounds.");
            return;
        }

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("OAUTH_PREFS",
                Context.MODE_MULTI_PROCESS);

        String token = prefs.getString("ACCESS_TOKEN", "");
        long expirationDate = prefs.getLong("EXPIRATION_TIMESTAMP", -1);

        Result<Sound> result = null;

        if ("".equals(token)) {
            Log.w(TAG, "there is no token");
        } else if (AuthUtils.isExpired(expirationDate)) {
            Log.w(TAG, "access_toke has expired.\nThis should be handled!!!!!\n");
        } else {
            Log.i(TAG, "access_token = " + token);

            FreesoundApiService service = new FreesoundRestClient(token).makeService();

            // Search results for a full day
            result = service.getResults("", "created:[* TO NOW]", "id,name,username,images",
                    "created_desc");
        }

        if (result == null) {
            Log.w(TAG, "\nno recent sounds received!\nNo access_token found.\nNeed to login\n\n");
        } else {
            Log.i(TAG, "\nrecent sounds received!\n\n");

            // Broadcast back once we have results.
            Intent receivedSoundsIntent = new Intent("RECENT_SOUNDS_RESULTS");
            receivedSoundsIntent.putExtra("RECENT_SOUNDS_RESULTS_DATA", result.toString());
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .sendBroadcast(receivedSoundsIntent);
        }
    }
}
