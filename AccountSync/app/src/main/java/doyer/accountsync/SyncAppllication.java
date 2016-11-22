package doyer.accountsync;

import android.app.Application;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import doyer.accountsync.account.MainActivity;

/**
 * Created by jie.du on 16/9/23.
 */

public class SyncAppllication extends Application {

    private Timer timer;
    private TimerTask task;


    @Override
    public void onCreate() {
        super.onCreate();

        task = new TimerTask() {
            @Override
            public void run() {
                MainActivity.writeFile(MainActivity.logFile, "----living----");
            }
        };
        timer = new Timer();
        timer.schedule(task, 10 * 1000, 10 * 1000);
        MainActivity.writeFile(MainActivity.logFile, "---app---start---");

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        MainActivity.writeFile(MainActivity.logFile, "---app---over---");
        Log.d("jie.du", "onTerminate: app over");
    }


}
