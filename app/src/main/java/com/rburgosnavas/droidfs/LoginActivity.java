package com.rburgosnavas.droidfs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.rburgosnavas.droidfs.api.clients.HttpAuthClient;
import com.rburgosnavas.droidfs.api.constants.Api;
import com.rburgosnavas.droidfs.api.models.TokenType;

import java.io.IOException;
import java.util.Calendar;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.rburgosnavas.droidfs.api.constants.Auth.ACCESS_TIMESTAMP;
import static com.rburgosnavas.droidfs.api.constants.Auth.ACCESS_TOKEN;
import static com.rburgosnavas.droidfs.api.constants.Auth.EXPIRATION_TIMESTAMP;
import static com.rburgosnavas.droidfs.api.constants.Auth.EXPIRES_IN;
import static com.rburgosnavas.droidfs.api.constants.Auth.OAUTH_PREFERENCES;
import static com.rburgosnavas.droidfs.api.constants.Auth.REFRESH_TOKEN;
import static com.rburgosnavas.droidfs.api.constants.Auth.SCOPE;


public class LoginActivity extends Activity implements
        LoginWebViewClient.OnAuthorizationCodeListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webView = (WebView) findViewById(R.id.webView);

        if (webView != null) {
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.clearCache(true);
            webView.clearFormData();
            webView.clearHistory();
            webView.getSettings().setSaveFormData(true);
            webView.setWebViewClient(new LoginWebViewClient(this));
            webView.loadUrl(Api.FS_OAUTH_AUTH_QUERY);
        } else {
            Log.e(TAG, "webView null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAuthorizationCode(String authorizationCode) {
        Log.i(TAG, "authorization code: " + authorizationCode);

        try {
            Log.i(TAG, "Getting access token...");
            Intent userLoginIntent = new Intent(this, MainActivity.class);

            HttpAuthClient.getAccessToken(authorizationCode, TokenType.ACCESS_TOKEN)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(oAuthToken -> {
                        Log.i(TAG, oAuthToken.toString());

                        SharedPreferences.Editor editor = getApplicationContext()
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

                        userLoginIntent.putExtra(ACCESS_TOKEN, oAuthToken.getAccessToken());
                    }, throwable -> {
                        Log.e(TAG, "Error getting access token: " + throwable);
                    }, () -> {
                        Log.i(TAG, "Done getting access token");
                        Log.i(TAG, "Starting MainActivity...");

                        setResult(Activity.RESULT_OK, userLoginIntent);
                        finish();
                    });
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}
