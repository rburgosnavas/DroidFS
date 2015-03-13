package com.rburgosnavas.droidfs.clients;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rburgosnavas.droidfs.exceptions.NullCallbackException;

/**
 * Created by rburgosnavas on 12/8/14.
 */
public class LoginWebViewClient extends WebViewClient {
    private static final String TAG = LoginWebViewClient.class.getSimpleName();

    private final OnAuthorizationCodeListener callback;

    public LoginWebViewClient(OnAuthorizationCodeListener callback) throws NullCallbackException {
        if (callback == null) {
            throw new NullCallbackException("LoginCallback is null or not implemented");
        }

        this.callback = callback;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.i(TAG, "WebView$onLoadResource -> url = " + url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i(TAG, "WebView$onPageStarted -> url = " + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.i(TAG, "WebView$onPageFinished -> url = " + url);

        Uri uri = Uri.parse(url);
        String code = uri.getQueryParameter("code");

        if (code != null && !code.isEmpty()) {
            Log.i(TAG, "CODE = " + code);
            callback.onAuthorizationCode(code);
        }
    }

    /**
     * Created by rburgosnavas on 12/8/14.
     */
    public static interface OnAuthorizationCodeListener {
        /**
         * Called when a valid authorization code is available.
         *
         * @param authorizationCode
         */
        public void onAuthorizationCode(String authorizationCode);
    }
}
