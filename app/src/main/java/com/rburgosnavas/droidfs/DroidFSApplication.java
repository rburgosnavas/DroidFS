package com.rburgosnavas.droidfs;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 *
 */
public class DroidFSApplication extends Application {
    RealmConfiguration configuration;

    @Override
    public void onCreate() {
        super.onCreate();
        configuration = new RealmConfiguration.Builder(getApplicationContext())
                .name("droidfs.realm")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}
