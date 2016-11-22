package doyer.synclib.accountsync.account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by jie.du on 16/10/20.
 */

public class Mainmanager {
    Account account;
    String mAccountname;
    String mPassword = "123";
    String ACCOUNT_TYPE;
    static String AUTHORITY;
    private AccountManager accountManager = null;
    public static Activity activity;

    private Bundle mResultBundle;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private PackageManager packageManager;
    private ApplicationInfo applicationInfo;

    public Mainmanager(Activity activity) {
        this.activity = activity;
        accountManager = AccountManager.get(activity);
    }

    public void setup() {
//        AUTHORITY = AccountAuthenticator.AccountType;

        try {
            packageManager = activity.getApplication().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(activity.getApplication().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        AUTHORITY = applicationName;
        ACCOUNT_TYPE = AUTHORITY;
        mAccountname = "doyer";
        mAccountAuthenticatorResponse = activity.getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        getAccount();
        if (null == account) {
            addAccount();
        }

        if (account != null) {
            ContentResolver.setMasterSyncAutomatically(true);
            ContentResolver.setIsSyncable(account, AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle.EMPTY, 10);

            triggerRefresh();
        }

    }


    private void triggerRefresh() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                account,
                AUTHORITY,
                b);
    }

    private String[] Permissions = {"android.permission.READ_SYNC_STATS", "android.permission.WRITE_SYNC_SETTINGS",
            "android.permission.READ_SYNC_SETTINGS", "android.permission.GET_ACCOUNTS",
            "android.permission.USE_CREDENTIALS", "android.permission.MANAGE_ACCOUNTS",
            "android.permission.AUTHENTICATE_ACCOUNTS"};

    private void getAccount() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, Permissions, 1);
            return;
        } else {
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if (accounts.length > 0) {
                account = accounts[0];
            }
        }
    }

    private void addAccount() {
        String name = mAccountname;
        String passwd = mPassword;

        Account account = new Account(name, ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, passwd, null);

        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, name);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        activity.setResult(Activity.RESULT_OK, intent);
        finish();

    }

    private final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }

    private void finish() {
        if (mAccountAuthenticatorResponse != null) {
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED, "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
    }
}
