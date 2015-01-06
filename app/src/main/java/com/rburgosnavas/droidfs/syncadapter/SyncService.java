package com.rburgosnavas.droidfs.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service {
    private static SyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("SyncService", "service created");
        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
                Log.i("SyncService", "SyncAdapter created");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SyncService", "service destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("SyncService", "service unbound | intent=" + intent.getDataString());
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("SyncService", "service rebound");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SyncService", "on start command");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("SyncService", "binding...");
        return syncAdapter.getSyncAdapterBinder();
    }
}
