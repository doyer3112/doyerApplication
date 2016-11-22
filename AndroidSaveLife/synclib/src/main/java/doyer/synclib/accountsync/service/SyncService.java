package doyer.synclib.accountsync.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import doyer.synclib.accountsync.adapter.SyncAdapter;


/**
 * Created by jie.du on 16/9/23.
 */

public class SyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SyncAdapter sSyncAdapter = null;
    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }

}
