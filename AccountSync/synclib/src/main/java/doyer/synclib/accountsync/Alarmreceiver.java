package doyer.synclib.accountsync;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import doyer.synclib.accountsync.account.Mainmanager;

/**
 * Created by jie.du on 16/11/15.
 */

public class Alarmreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("sync", "Sync: request");
        Mainmanager mainManager = new Mainmanager(Mainmanager.activity);
        mainManager.setup();
    }
}
