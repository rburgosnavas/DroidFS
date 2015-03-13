package com.rburgosnavas.droidfs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.rburgosnavas.droidfs.clients.LoginWebViewClient;
import com.rburgosnavas.droidfs.constants.Constants;
import com.rburgosnavas.droidfs.exceptions.NullCallbackException;
import com.rburgosnavas.droidfs.models.TokenType;
import com.rburgosnavas.droidfs.syncadapter.AuthTask;


public class LoginActivity extends Activity implements
        LoginWebViewClient.OnAuthorizationCodeListener,
        AuthTask.AuthListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.getSettings().setSaveFormData(true);

        try {
            webView.setWebViewClient(new LoginWebViewClient(this));
        } catch (NullCallbackException e) {
            e.printStackTrace();
        }

        webView.loadUrl(Constants.FS_OAUTH_AUTH_QUERY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAuthorizationCode(String authorizationCode) {
        new AuthTask(getApplicationContext(), TokenType.ACCESS_TOKEN, this)
                .execute(authorizationCode);
    }

    @Override
    public void onAuthPreExecute() { }

    @Override
    public void onAuthProgress() { }

    @Override
    public void onAuthPostExecute() {
        Intent userLoginIntent = new Intent(this, MainActivity.class);
        setResult(Activity.RESULT_OK, userLoginIntent);
        finish();
    }

    @Override
    public void onAuthCancelled() { }
}
