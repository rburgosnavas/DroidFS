package com.rburgosnavas.droidfs.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.rburgosnavas.droidfs.R;
import com.rburgosnavas.droidfs.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.httpservices.FreesoundApiService;
import com.rburgosnavas.droidfs.models.Result;
import com.rburgosnavas.droidfs.models.Sound;
import com.rburgosnavas.droidfs.utils.AuthUtils;
import com.rburgosnavas.droidfs.utils.Skal;

/**
 * This service gets started by StartRecentSoundsServiceReceiver.
 * This service will also be bound by the activity in question.
 */
public class RecentSoundsService extends Service {
    private static final String TAG = RecentSoundsService.class.getSimpleName();

    private LocalBinder binder = new LocalBinder();

    public RecentSoundsService() { }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "created");
        Skal.make(getApplicationContext(), true, "RecentSoundsService created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "destroyed");
        Skal.make(getApplicationContext(), "RecentSoundsService destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "binding");
        Skal.make(getApplicationContext(), "RecentSoundsService bound");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "started");
        Skal.make(getApplicationContext(), "RecentSoundsService started");

        // Get recent sounds.
        getRecentSounds();

        return START_STICKY;
    }

    /**
     * Fetch the recent sounds, save them in persistence, and broadcast back.
     */
    public void getRecentSounds() {
        Log.i(TAG, "getting recent sounds");

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        Log.i(TAG, "Current network info: " + ni);

        boolean isConnected = ni != null && ni.isConnected();

        if (!isConnected) {
            Log.e(TAG, "Network not connected.\nCould not retrieve sounds.");

            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("DroidFS - Warning")
                    .setContentText("Network not connected! Could not retrieve sounds!")
                    /*.setVibrate(new long[]{0, 2000, 1000})*/;

            NotificationManager manager = (NotificationManager) getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(607, builder.build());

            return;
        }

        new AsyncTask<Void, Void, Result<Sound>>() {
            private SharedPreferences prefs;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prefs = getApplicationContext().getSharedPreferences("OAUTH_PREFS",
                        Context.MODE_MULTI_PROCESS);
            }

            @Override
            protected Result<Sound> doInBackground(Void... params) {
                String token = prefs.getString("ACCESS_TOKEN", "");
                long expirationDate = prefs.getLong("EXPIRATION_TIMESTAMP", -1);

                if ("".equals(token)) {
                    Log.w(TAG, "there is no token");
                } else if (AuthUtils.isExpired(expirationDate)) {
                    Log.w(TAG, "access_toke has expired.\nThis should be handled!!!!!\n");
                } else {
                    Log.i(TAG, "access_token = " + token);

                    FreesoundApiService service = new FreesoundRestClient(token).makeService();

                    // Search results for a full day
                    Result<Sound> result = service.getResults(
                            "",
                            "created:[NOW-1DAY TO NOW]",
                            "id,name,tags,username,url,images",
                            "created_desc");

                    return result;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Result<Sound> result) {
                super.onPostExecute(result);
                if (result == null) {
                    Log.w(TAG, "\nno recent sounds received!\nNo access_token found.\nNeed to login\n\n");
                } else {
                    Log.i(TAG, "\nrecent sounds received!\n\n");
                }

                // Broadcast back once we have results.
                Intent test = new Intent("RECENT_SOUNDS_RESULTS");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(test);
            }
        }.execute();
    }

    public class LocalBinder extends Binder {
        public RecentSoundsService getService() {
            return RecentSoundsService.this;
        }
    }
}
