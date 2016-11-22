package doyer.accountsync.account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import doyer.accountsync.R;

public class MainActivity extends AppCompatActivity {

    public static final String ARG_ACCOUNT_NAME = "doyer";
    Account account;
    String mAccountname = "doyer";
    String mPassword = "123";
    String ACCOUNT_TYPE;
    String ACCOUNT_AUTHORITY;
    private AccountManager accountManager = null;

    public static File logFile = new File(Environment.getExternalStorageDirectory(), "APPSync.txt");
    private String TAG = "jie.du";

    private Bundle mResultBundle;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountManager = AccountManager.get(this);

        ACCOUNT_TYPE = getString(R.string.account_auth_type);
        ACCOUNT_AUTHORITY = getString(R.string.account_auth_provide);

        mAccountAuthenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }

        getAccount();
        if (null == account) {
            addAccount();
        }

        if (account != null) {
            ContentResolver.setMasterSyncAutomatically(true);
            ContentResolver.setIsSyncable(account, ACCOUNT_AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, ACCOUNT_AUTHORITY, true);
            ContentResolver.addPeriodicSync(account, ACCOUNT_AUTHORITY, new Bundle(), 10);
            triggerRefresh();
        }

        writeFile(logFile, "---app--activity--start---");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeFile(logFile, "---app--activity--destory---");

    }

    public void triggerRefresh() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(
                account,
                ACCOUNT_AUTHORITY,
                b);
    }

    public static void writeFile(File logFile, String logLine) {
        try {
            FileOutputStream outputStream = new FileOutputStream(logFile, true);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(getCurTime() + ": " + logLine + "\n");
            writer.flush();
            writer.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurTime() {
        Calendar cal = Calendar.getInstance();
        return "" + cal.get(Calendar.YEAR) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH) + " "
                + cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getAccount();
        }
    }


    private String[] Permissions = {"android.permission.READ_SYNC_STATS", "android.permission.WRITE_SYNC_SETTINGS",
            "android.permission.READ_SYNC_SETTINGS", "android.permission.GET_ACCOUNTS",
            "android.permission.USE_CREDENTIALS", "android.permission.MANAGE_ACCOUNTS",
            "android.permission.AUTHENTICATE_ACCOUNTS"};

    private void getAccount() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Permissions, 1);
            return;
        } else {
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            if (accounts.length > 0) {
                account = accounts[0];
            }
        }
    }

    public void addAccount() {
        String name = mAccountname;
        String passwd = mPassword;

        Account account = new Account(name, ACCOUNT_TYPE);
        accountManager.addAccountExplicitly(account, passwd, null);

        final Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, name);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();

    }

    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }

    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED, "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }


}
