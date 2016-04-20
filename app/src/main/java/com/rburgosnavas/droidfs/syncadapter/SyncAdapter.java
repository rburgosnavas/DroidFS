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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rburgosnavas.droidfs.R;
import com.rburgosnavas.droidfs.api.clients.HttpAuthClient;
import com.rburgosnavas.droidfs.api.models.TokenType;
import com.rburgosnavas.droidfs.utils.AuthUtils;

import java.io.IOException;
import java.util.Calendar;

import rx.schedulers.Schedulers;

import static com.rburgosnavas.droidfs.api.constants.Auth.*;

/**
 * http://developer.android.com/training/sync-adapter/index.html
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter{
    private static final String TAG = SyncAdapter.class.getSimpleName();
    private final SharedPreferences prefs;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        Log.i(TAG, "sync adapter created");
        prefs = context.getSharedPreferences(OAUTH_PREFERENCES,
                Context.MODE_MULTI_PROCESS);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        Log.i(TAG, "sync adapter created");
        prefs = context.getSharedPreferences(OAUTH_PREFERENCES,
                Context.MODE_MULTI_PROCESS);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.w(TAG, "performing sync");

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        Log.i(TAG, "Current network info: " + ni);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_launcher);

        NotificationManager manager = (NotificationManager) getContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (!(ni != null && ni.isConnected())) {
            Log.e(TAG, "Network is not connected.");

            builder.setContentTitle("DroidFS - Warning")
                    .setContentText("Network not connected");
            manager.notify(606, builder.build());

            return;
        } else {
            Log.i(TAG, "Network is connected.");

            builder.setContentTitle("DroidFS - Syncing")
                    .setContentText("Syncing account");
        }

        manager.notify(606, builder.build());

        if (!prefs.contains(ACCESS_TOKEN)) {
            Log.e(TAG, "No access token found!");
            return;
        }

        long expiration = prefs.getLong(EXPIRATION_TIMESTAMP, -1);
        String accessCode = prefs.getString(ACCESS_TOKEN, "");

        if (AuthUtils.isExpired(expiration)) {
            Log.w(TAG, "access_token=" + accessCode + " expired.");

            final String refreshToken = prefs.getString(REFRESH_TOKEN, "");
            Log.w(TAG, "using refresh_token=" + refreshToken + " to validate.");

            if (!"".equals(refreshToken)) {
                Log.i(TAG, "getting new token from refresh_token");
                Log.i(TAG, "attempting to get new token!");

                try {
                    HttpAuthClient.getAccessToken(refreshToken, TokenType.REFRESH_TOKEN)
                            .observeOn(Schedulers.immediate())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(oAuthToken -> {
                                SharedPreferences.Editor editor = getContext()
                                        .getSharedPreferences(OAUTH_PREFERENCES,
                                                Context.MODE_PRIVATE).edit();

                                editor.putString(ACCESS_TOKEN, oAuthToken.getAccessToken());
                                editor.putString(SCOPE, oAuthToken.getScope());
                                editor.putLong(EXPIRES_IN, oAuthToken.getExpiresIn());
                                editor.putString(REFRESH_TOKEN, oAuthToken.getRefreshToken());

                                // Removing one hour from expiration timestamp so that the services starts
                                // getting a new token before current token expires (a precaution)
                                Calendar c = Calendar.getInstance();
                                editor.putLong(ACCESS_TIMESTAMP, c.getTimeInMillis());
                                c.set(Calendar.SECOND, (int) oAuthToken.getExpiresIn());
                                c.add(Calendar.HOUR, -1);
                                // c.add(Calendar.MINUTE, -50);

                                editor.putLong(EXPIRATION_TIMESTAMP, c.getTimeInMillis());
                                editor.apply();

                                Log.i(TAG, "new token saved (will expire on " + c.getTime() + ")");
                            }, t -> {

                            }, () -> {
                                NotificationCompat.Builder syncSuccessNotification =
                                        new NotificationCompat.Builder(getContext())
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("DroidFS - Sync complete")
                                        .setContentText("Access token renewed");
                                manager.notify(606, syncSuccessNotification.build());

                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (!"".equals(accessCode)) {
            Log.i(TAG, "access_token=" + accessCode + " valid.");

            builder.setContentTitle("DroidFS - Sync complete")
                    .setContentText("Access token is still valid");
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
