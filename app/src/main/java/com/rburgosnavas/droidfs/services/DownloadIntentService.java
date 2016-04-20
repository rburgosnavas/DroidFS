package com.rburgosnavas.droidfs.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.rburgosnavas.droidfs.api.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.api.services.FreesoundApiService;
import com.rburgosnavas.droidfs.utils.Source;

import retrofit2.Response;

/**
 * DownloadIntentService
 */
public class DownloadIntentService extends IntentService {
    private static final String TAG = DownloadIntentService.class.getSimpleName();

    public DownloadIntentService() {
        super("DownloadIntentService");
        Log.i(TAG, "service started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "handling intent");

        String accessToken = intent.getStringExtra("ACCESS_TOKEN");
        Log.i(TAG, "access_token = " + accessToken);

        // User, pack, and sound name needed to create directories and save file to disk.
        String userName = intent.getStringExtra("USER_NAME");
        String packName = intent.getStringExtra("PACK_NAME");
        String soundName = intent.getStringExtra("SOUND_NAME");

        // Sound ID needed to download from URL
        String soundId = intent.getStringExtra("SOUND_ID");

        FreesoundRestClient client = FreesoundRestClient.newInstance(accessToken);
        FreesoundApiService service = client.makeService();
        Response res = service.downloadSound(soundId);

        Source.writeSound(res.raw().body().byteStream(), userName, packName, soundName);
        Log.v(TAG, "Wrote " + userName + "/" + packName + "/" + soundName);
    }
}
