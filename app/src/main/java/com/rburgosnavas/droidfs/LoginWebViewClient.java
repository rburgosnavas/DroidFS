package com.rburgosnavas.droidfs;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 */
public class LoginWebViewClient extends WebViewClient {
    private static final String TAG = LoginWebViewClient.class.getSimpleName();

    private final OnAuthorizationCodeListener callback;

    public LoginWebViewClient(OnAuthorizationCodeListener callback) {
        this.callback = callback;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.i(TAG, "onLoadResource -> url = " + url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "onPageStarted -> url = " + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "onPageFinished -> url = " + url);

        String code = Uri.parse(url).getQueryParameter("code");

        if (code != null && !code.isEmpty()) {
            Log.i(TAG, "code = " + code);
            callback.onAuthorizationCode(code);
        }
    }

    /**
     * Created by rburgosnavas on 12/8/14.
     */
    public interface OnAuthorizationCodeListener {
        /**
         * Called when a valid authorization code is available.
         *
         * @param authorizationCode
         */
        public void onAuthorizationCode(String authorizationCode);
    }
}
