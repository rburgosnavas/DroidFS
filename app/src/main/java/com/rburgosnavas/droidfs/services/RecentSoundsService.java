package com.rburgosnavas.droidfs.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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

        new AsyncTask<Void, Void, Result<Sound>>() {
            private SharedPreferences prefs;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prefs = getApplicationContext().getSharedPreferences("OAUTH_PREFS", Context.MODE_PRIVATE);
            }

            @Override
            protected Result<Sound> doInBackground(Void... params) {
                String token = prefs.getString("ACCESS_TOKEN", "");

                if ("".equals(token)) {
                    Log.w(TAG, "there is no token");
                } else if (AuthUtils.isExpired(prefs.getLong("EXPIRATION_TIMESTAMP", -1))) {
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
                Log.i(TAG, "\nrecent sounds received!\n\n");

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
