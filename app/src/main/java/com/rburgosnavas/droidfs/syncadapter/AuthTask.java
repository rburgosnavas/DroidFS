package com.rburgosnavas.droidfs.syncadapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rburgosnavas.droidfs.clients.HttpAuthClient;
import com.rburgosnavas.droidfs.models.TokenType;

import java.io.IOException;
import java.util.Calendar;

/**
* Created by rburgosnavas on 12/10/14.
*/
public class AuthTask extends AsyncTask<String, String, JsonObject> {
    private static final String TAG = AuthTask.class.getSimpleName();

    private final TokenType tokenType;
    private final Context context;
    private final AuthListener authListener;

    public AuthTask(Context context, TokenType tokenType, AuthListener authListener) {
        this.context = context;
        this.tokenType = tokenType;
        this.authListener = authListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        authListener.onAuthPreExecute();
    }

    @Override
    protected JsonObject doInBackground(String... params) {
        String bodyString = null;
        try {
            if (tokenType == TokenType.ACCESS_TOKEN) {
                Log.i(TAG, "getting access token");
                bodyString = HttpAuthClient.getAccessToken(params[0]).body().string();
            } else {
                Log.i(TAG, "getting refresh token");
                bodyString = HttpAuthClient.getRefreshToken(params[0]).body().string();
            }
            Log.i(TAG, "RESPONSE = " + bodyString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject j = new JsonParser().parse(bodyString).getAsJsonObject();

        return j;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        authListener.onAuthProgress();
    }

    @Override
    protected void onPostExecute(JsonObject token) {
        super.onPostExecute(token);

        SharedPreferences prefs = context.getSharedPreferences("OAUTH_PREFS", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ACCESS_TOKEN", token.get("access_token").getAsString());

        Calendar c = Calendar.getInstance();
        editor.putLong("ACCESS_TIMESTAMP", c.getTimeInMillis());

        int time = token.get("expires_in").getAsInt();
        c.set(Calendar.SECOND, time);

        // TODO: remove, only for testing
        c.add(Calendar.HOUR, -23);
        c.add(Calendar.MINUTE, -50);

        editor.putInt("EXPIRES_IN", time);
        editor.putLong("EXPIRATION_TIMESTAMP", c.getTimeInMillis());
        editor.putString("REFRESH_TOKEN", token.get("refresh_token").getAsString());
        editor.apply();

        authListener.onAuthPostExecute();
    }

    @Override
    protected void onCancelled(JsonObject token) {
        super.onCancelled(token);
        authListener.onAuthCancelled();
    }

    /**
     * Listener can be used to make widget updates within an activity to avoid passing objects to
     * AuthTask. Methods reflect AuthTask's AsyncTask methods.
     */
    public static interface AuthListener {
        // TODO Maybe mirror these methods' arguments like AsyncTask?
        /**
         * Called from AuthTask.onPreExecute()
         */
        public void onAuthPreExecute();

        /**
         * Called from AuthTask.onProgressUpdate(...)
         */
        public void onAuthProgress();

        /**
         * Called from AuthTask.onPostExecute(...)
         */
        public void onAuthPostExecute();

        /**
         * Called from AuthTask.onCancelled()
         */
        public void onAuthCancelled();
    }
}
