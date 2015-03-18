package com.rburgosnavas.droidfs.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.rburgosnavas.droidfs.R;

/**
 * Created by rburgosnavas on 12/15/14.
 */
public class SyncUtils {
    public static final String TAG = SyncUtils.class.getSimpleName();

    private static final long HOURS = 1;
    private static final long MINUTES = 1;
    private static final long SECONDS = 60;

    /**
     * 1 hour.
     * <br>
     * <br>
     * TODO: change HOURS as needed.
     */
    private static final long POLL_FREQUENCY = HOURS * MINUTES * SECONDS;

    /**
     * Create a new account for the sync adapter.
     *
     * @param context The application context
     * @param user The user name
     */
    public static void createSyncAccount(Context context, String user) {
        // Create the account type and default account
        Account account = new Account(user, context.getString(R.string.sync_account_type));
        Log.i(TAG, "account created");

        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        boolean isAdded = accountManager.addAccountExplicitly(account, null, null);

        Bundle bundle = new Bundle();

        if (isAdded) {
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, false);
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, false);

            // Sync every hour
            ContentResolver.setMasterSyncAutomatically(true);
            ContentResolver.setSyncAutomatically(account,
                    context.getString(R.string.sync_adapter_authority), true);
            ContentResolver.addPeriodicSync(account,
                    context.getString(R.string.sync_adapter_authority), bundle, POLL_FREQUENCY);
            Log.i(TAG, "added periodically");
        } else {
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(account,
                    context.getString(R.string.sync_adapter_authority), bundle);
            Log.i(TAG, "request sync");
        }
    }
}
