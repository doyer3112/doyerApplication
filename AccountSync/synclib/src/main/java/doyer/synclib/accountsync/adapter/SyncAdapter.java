package doyer.synclib.accountsync.adapter;


import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import doyer.synclib.accountsync.Alarmreceiver;

import static android.content.Context.ALARM_SERVICE;


public class SyncAdapter extends AbstractThreadedSyncAdapter {


    /*private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Log.e("sync", "Sync: request");
            Mainmanager mainManager = new Mainmanager(Mainmanager.activity);
            mainManager.setup();
        }
    };
    private Timer timer = new Timer();*/


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        Intent intent = new Intent(context, Alarmreceiver.class);
        intent.setAction("repeating");
        PendingIntent sender = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        //开始时间
        long firstime = SystemClock.elapsedRealtime();

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //60秒一个周期，不停的发送广播
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 60 * 1000, sender);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        Intent intent = new Intent(context, Alarmreceiver.class);
        intent.setAction("repeating");
        PendingIntent sender = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        //开始时间
        long firstime = SystemClock.elapsedRealtime();

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //60秒一个周期，不停的发送广播
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, 60 * 1000, sender);
//        timer.schedule(task, 1000, 60 * 1000);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //此方法执行在一个独立的线程中 可以做耗时操作
        Log.e("Sync", "Sync started!");
        /*if (timer == null) {
            timer = new Timer();
            timer.schedule(task, 1000, 60 * 1000);
        }*/
    }
}
