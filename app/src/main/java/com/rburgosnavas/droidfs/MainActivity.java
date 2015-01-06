package com.rburgosnavas.droidfs;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rburgosnavas.droidfs.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.httpservices.FreesoundApiService;
import com.rburgosnavas.droidfs.models.Pack;
import com.rburgosnavas.droidfs.models.Result;
import com.rburgosnavas.droidfs.models.User;
import com.rburgosnavas.droidfs.receivers.RecentSoundsResultsReceiver;
import com.rburgosnavas.droidfs.receivers.ScheduledRecentSoundsReceiver;
import com.rburgosnavas.droidfs.services.DownloadService;
import com.rburgosnavas.droidfs.services.RecentSoundsService;
import com.rburgosnavas.droidfs.utils.SyncUtils;

import java.io.File;

import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class MainActivity extends Activity implements ServiceConnection, RecentSoundsResultsReceiver.ResultsListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecentSoundsService recentSoundsService;
    private RecentSoundsResultsReceiver recentSoundsResultsReceiver;

    private TextView tv, expTimeTv;
    private Button downloadBtn, uploadBtn;
    private SharedPreferences prefs;

    @Override
    protected void onStart() {
        super.onStart();

        // TODO Is this the best way to start the RecentSoundService when the activity starts?
        Intent recentSoundsService = new Intent(this, RecentSoundsService.class);
        startService(recentSoundsService);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This receiver will notify the activity when results have been fetched.
        recentSoundsResultsReceiver = new RecentSoundsResultsReceiver();
        recentSoundsResultsReceiver.setResultsListener(this);

        ScheduledRecentSoundsReceiver.scheduleAlarm(this);

        prefs = getApplicationContext().getSharedPreferences("OAUTH_PREFS", Context.MODE_PRIVATE);

        // If the app is started for the first time, there will be no access token.
        // The application will need to start a login activity to have the user login. This way the
        // application gets the token needed to proceed.
        if (!prefs.contains("ACCESS_TOKEN")) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, 606);
        }

        tv = (TextView) findViewById(R.id.tv);
        expTimeTv = (TextView) findViewById(R.id.expTimeTv);

        downloadBtn = (Button) findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DownloadService.class);
                i.putExtra("ACCESS_TOKEN", prefs.getString("ACCESS_TOKEN", ""));

                // Hard coding the user name, pack, and sound to download for testing purposes.
                i.putExtra("USER_NAME", "Freed");
                i.putExtra("PACK_NAME", "83");
                i.putExtra("SOUND_NAME", "180404d.mp3");
                i.putExtra("SOUND_ID", "1234");
                getApplicationContext().startService(i);
            }
        });

        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        FreesoundRestClient client =
                                new FreesoundRestClient(prefs.getString("ACCESS_TOKEN", ""));
                        FreesoundApiService ss = client.makeService();

                        Response res = ss.uploadSound(new TypedFile("audio/mpeg",
                                new File(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_DOWNLOADS),
                                        "1234__freed__180404d.mp3")), null, null, null, null, null, null);

                        Log.i(TAG, "UPLOAD-URL = " + res.getUrl());
                        Log.i(TAG, "UPLOAD-REASON = " + res.getReason());
                        Log.i(TAG, "UPLOAD-STATUS = " + res.getStatus() + "");
                        Log.i(TAG, "UPLOAD-HEADERS = " + res.getHeaders().toString());
                        Log.i(TAG, "UPLOAD-BODY = " + res.getBody().toString());

                        return null;
                    }
                }.execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register this activity to receive broadcasts for recent sounds.
        IntentFilter filter = new IntentFilter("RECENT_SOUNDS_RESULTS");
        LocalBroadcastManager.getInstance(this).registerReceiver(recentSoundsResultsReceiver, filter);

        // Bind this activity (application?) to the recent sounds service.
        Intent bindIntent = new Intent(this, RecentSoundsService.class);
        bindService(bindIntent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(recentSoundsResultsReceiver);

        if (recentSoundsService != null) {
            unbindService(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // After login from LoginActivity, get 'me' from API, get user name from 'me', and
            // create sync account for user.
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    FreesoundRestClient client =
                            new FreesoundRestClient(prefs.getString("ACCESS_TOKEN", ""));
                    FreesoundApiService service = client.makeService();

                    User me = service.getMe();
                    Log.i(TAG, "ME = " + me.toString());

                    return me.getUserName();
                }

                @Override
                protected void onPostExecute(String user) {
                    super.onPostExecute(user);

                    // Once a user name from the 'me' data is fetched, create a sync adapter.
                    SyncUtils.createSyncAccount(getApplicationContext(), user);
                }
            }.execute();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_recent_sounds:NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("DroidFS - Recent sounds")
                    .setContentText("Got new sounds!");

                NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(607, builder.build());
                return true;
            case R.id.action_me:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        FreesoundRestClient client =
                                new FreesoundRestClient(prefs.getString("ACCESS_TOKEN", ""));
                        FreesoundApiService ss = client.makeService();

                        User me = ss.getMe();
                        Log.i(TAG, "ME = " + me.toString());

                        return null;
                    }
                }.execute();
                return true;
            case R.id.action_my_packs:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        FreesoundRestClient client =
                                new FreesoundRestClient(prefs.getString("ACCESS_TOKEN", ""));
                        FreesoundApiService service = client.makeService();

                        Result<Pack> packs = service.getUserPacks("sweet_trip");
                        Log.i(TAG, "PACK = " + packs.toString());

                        return null;
                    }
                }.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        recentSoundsService = ((RecentSoundsService.LocalBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        recentSoundsService = null;
    }

    @Override
    public void onResultsReceive(Context context, Intent intent) {
        // Once recent sounds have been fetched by the service, the service will notify
        // RecentSoundsResultsReceiver, which in turn will notify this activity via this callback
        // method.
        //
        // In theory, the application will grab the recent sounds from persistence and display them
        // in the front end.
        Log.i(TAG, "received:\n" + intent);
    }
}