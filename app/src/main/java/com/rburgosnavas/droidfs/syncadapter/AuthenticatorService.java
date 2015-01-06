package com.rburgosnavas.droidfs.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AuthenticatorService extends Service {
    private Authenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new Authenticator(this);
        Log.i("AuthenticatorService", "created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AuthenticatorService", "on start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("AuthenticatorService", "destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("AuthenticatorService", "unbound");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("AuthenticatorService", "rebound");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("AuthenticatorService", "binding...");
        return authenticator.getIBinder();
    }
}
