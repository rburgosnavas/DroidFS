package com.rburgosnavas.droidfs.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.rburgosnavas.droidfs.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.httpservices.FreesoundApiService;
import com.rburgosnavas.droidfs.utils.Source;

import java.io.IOException;

import retrofit.client.Response;

/**
 * DownloadService
 */
public class DownloadService extends IntentService {
    private static final String TAG = DownloadService.class.getSimpleName();

    public DownloadService() {
        super("DownloadService");
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

        FreesoundRestClient client = new FreesoundRestClient(accessToken);
        FreesoundApiService service = client.makeService();
        Response res = service.downloadSound(soundId);
        Log.i(TAG, "Download successful!" +
                "\nStatus = " + res.getStatus() +
                "\nReason = " + res.getReason() +
                "\nHeaders = " + res.getHeaders().toString());

        // Old version looked up and parsed the file name from the header. Since ultimately the
        // user will download the file from a list, the app should get the actual name of the file
        // to download (along with the user and pack name) from the information used to populate
        // the list.
        //
        // TODO delete these block when ready
        //
        // String fileName = "";
        //
        // for (Header h : res.getHeaders()) {
        //     if ("Content-Disposition".equals(h.getName())) {
        //         String[] vals = h.getValue().split("=");
        //         for (String v : vals) {
        //             if (v.contains("mp3")) {
        //                 fileName = v.replaceAll("\"", "");
        //             }
        //         }
        //     }
        // }

        try {
            Source.writeSound(res.getBody().in(), userName, packName, soundName);
            Log.v(TAG, "Wrote " + userName + "/" + packName + "/" + soundName);
        } catch (IOException e) {
            Log.e(TAG, "Failed to write " + userName + "/" + packName + "/" + soundName);
            e.printStackTrace();
        }
    }
}
