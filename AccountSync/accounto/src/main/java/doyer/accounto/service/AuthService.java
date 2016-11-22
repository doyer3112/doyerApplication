package doyer.accounto.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import doyer.accounto.account.AccountAuthenticator;


/**
 * Created by jie.du on 16/9/23.
 */

public class AuthService extends Service {
    private static final String ACCOUNT_TYPE = "doyer.accountsync";
    private AccountAuthenticator mAuthenticator;

    private AccountAuthenticator authenticator;

    public AuthService() {
        authenticator = new AccountAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
