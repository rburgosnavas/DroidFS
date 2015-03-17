package com.rburgosnavas.droidfs.syncadapter;

import android.accounts.Account;
import android.app.NotificationManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rburgosnavas.droidfs.R;
import com.rburgosnavas.droidfs.clients.HttpAuthClient;
import com.rburgosnavas.droidfs.utils.AuthUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * http://developer.android.com/training/sync-adapter/index.html
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter{
    private static final String TAG = SyncAdapter.class.getSimpleName();
    private final SharedPreferences prefs;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        Log.i(TAG, "sync adapter created");
        prefs = context.getSharedPreferences("OAUTH_PREFS", Context.MODE_MULTI_PROCESS);
    }

    public SyncAdapter(Context context, boolean autoInitialize,
                       boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        Log.i(TAG, "sync adapter created");
        prefs = context.getSharedPreferences("OAUTH_PREFS", Context.MODE_MULTI_PROCESS);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.w(TAG, "performing sync");

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        Log.i(TAG, "Current network info: " + ni);

        boolean isConnected = ni != null && ni.isConnected();

        if (!isConnected) {
            Log.e(TAG, "Network not connected.\nNo data will be pulled.");

            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(getContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("DroidFS - Warning")
                    .setContentText("Network not connected!")
                    /*.setVibrate(new long[]{0, 2000, 1000})*/;

            NotificationManager manager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(606, builder.build());

            return;
        } else {
            Log.i(TAG, "Network is connected.\n");

            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(getContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("DroidFS - Syncing")
                    .setContentText("Performing syncing of account")
                    /*.setVibrate(new long[]{0, 2000, 1000})*/;

            NotificationManager manager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(606, builder.build());
        }

        if (!prefs.contains("ACCESS_TOKEN")) {
            Log.e(TAG, "No ACCESS_TOKEN found!");
            return;
        }

        long expiration = prefs.getLong("EXPIRATION_TIMESTAMP", -1);
        String accessCode = prefs.getString("ACCESS_TOKEN", "");

        if (AuthUtils.isExpired(expiration)) {
            Log.w(TAG, "access_token=" + accessCode + " expired.");

            final String refreshToken = prefs.getString("REFRESH_TOKEN", "");
            Log.w(TAG, "using refresh_token=" + refreshToken + " to validate.");

            if (!"".equals(refreshToken)) {
                Log.i(TAG, "getting new token from refresh_token");

                new AsyncTask<String, String, JsonObject>() {

                    @Override
                    protected JsonObject doInBackground(String... params) {
                        Log.i(TAG, "attempting to get new token!");

                        String bodyString = null;
                        try {
                            Log.i(TAG, "getting refresh token");
                            bodyString =
                                    HttpAuthClient.getRefreshToken(params[0]).body().string();
                            Log.i(TAG, "RESPONSE = " + bodyString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return new JsonParser().parse(bodyString).getAsJsonObject();
                    }

                    @Override
                    protected void onPostExecute(JsonObject token) {
                        super.onPostExecute(token);

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("ACCESS_TOKEN", token.get("access_token").getAsString());

                        Calendar c = Calendar.getInstance();
                        editor.putLong("ACCESS_TIMESTAMP", c.getTimeInMillis());
                        Log.i(TAG, "new token accessed on " + c.getTime());

                        int time = token.get("expires_in").getAsInt();
                        c.set(Calendar.SECOND, time);

                        // TODO: remove, only for testing
                        c.add(Calendar.HOUR, -23);
                        c.add(Calendar.MINUTE, -50);

                        editor.putInt("EXPIRES_IN", time);
                        editor.putLong("EXPIRATION_TIMESTAMP", c.getTimeInMillis());
                        editor.putString("REFRESH_TOKEN", token.get("refresh_token").getAsString());
                        editor.apply();

                        Log.i(TAG, "new token saved (will expire on " + c.getTime() + ")");

                        NotificationCompat.Builder builder = new NotificationCompat
                                .Builder(getContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("DroidFS - Sync complete")
                                .setContentText("Access token will expire on " + c.getTime())
                                    /*.setVibrate(new long[]{0, 2000, 1000})*/;

                        NotificationManager manager = (NotificationManager) getContext()
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(606, builder.build());
                    }
                }.execute(refreshToken);
            }
        } else if (!"".equals(accessCode)) {
            Log.i(TAG, "access_token=" + accessCode + " valid.");

            NotificationCompat.Builder builder = new NotificationCompat
                    .Builder(getContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("DroidFS - Sync complete")
                    .setContentText("Access token is still valid")
                        /*.setVibrate(new long[]{0, 200, 1000})*/;

            NotificationManager manager = (NotificationManager) getContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(606, builder.build());
        } else {
            Log.w(TAG, "no access token found (need to log in)");
        }
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
        Log.w(TAG, "cancelled");
    }

    @Override
    public void onSyncCanceled(Thread thread) {
        super.onSyncCanceled(thread);
        Log.w(TAG, "cancelled (thread=" + thread.toString() + ")");
    }
}
