package com.rburgosnavas.droidfs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rburgosnavas.droidfs.api.clients.FreesoundRestClient;
import com.rburgosnavas.droidfs.api.constants.Auth;
import com.rburgosnavas.droidfs.api.services.FreesoundApiService;
import com.rburgosnavas.droidfs.db.SearchCategoryObject;
import com.rburgosnavas.droidfs.utils.SyncUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Realm realm;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        SharedPreferences prefs =
                getApplicationContext().getSharedPreferences(Auth.OAUTH_PREFERENCES,
                        Context.MODE_PRIVATE);

        // If app starts for the first time, start login activity, get user to log in, then come
        // to this activity with an access token to continue (see onActivityResult())
        if (!prefs.contains(Auth.ACCESS_TOKEN)) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, 606);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");

        realm = Realm.getDefaultInstance();

//        realm.beginTransaction();
//
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"noise\", searchTerms: \"noise\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"beats\", searchTerms: \"beats\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"drums\", searchTerms: \"drums\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"bass\", searchTerms: \"bass\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"synth\", searchTerms: \"synth\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"guitar\", searchTerms: \"guitar\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"loop\", searchTerms: \"loop\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"pad\", searchTerms: \"pad\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"vocal\", searchTerms: \"vocal\"}");
//        realm.createObjectFromJson(SearchCategoryObject.class, "{name: \"glitch\", searchTerms: \"glitch\"}");
//
//        realm.commitTransaction();

        realm.where(SearchCategoryObject.class).findAll().asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(searchCategoryObjects -> {
                    List<String> names = new ArrayList<>();

                    for (SearchCategoryObject s : searchCategoryObjects) {
                        names.add(s.getName());
                    }

                    return names;
                })
                .subscribe(names -> {
                    Log.i(TAG, "Got search categories: " + names.toString());
                }, throwable -> {
                    Log.e(TAG, "Error getting search categories from db", throwable);
                }, () -> {
                    Log.i(TAG, "Done getting search categories from db");
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        realm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult(...)");
        Log.i(TAG, "Getting user info...");

        String accessCode = data.getStringExtra(Auth.ACCESS_TOKEN);

        // After login from LoginActivity, get user name and create sync account.
        if (resultCode == Activity.RESULT_OK && accessCode != null) {
            FreesoundRestClient client = FreesoundRestClient.newInstance(accessCode);
            FreesoundApiService service = client.makeService();
            service.getMe()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(user -> {
                        Log.i(TAG, "Got " + user);
                        Log.i(TAG, "Creating sync account...");
                        SyncUtils.createSyncAccount(getApplicationContext(), user.getUserName());
                    }, t -> {
                        Log.e(TAG, t.getMessage());
                    }, () -> {
                        Log.i(TAG, "Done getting user info");
                    });
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: implement if needed
        return false;
    }
}
