package doyer.hello;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jie.du on 16/10/19.
 */
public class HelloApplication extends Application {

    private Timer timer;
    private TimerTask task;
    public static File logFile = new File(Environment.getExternalStorageDirectory(), "Custom.txt");

    @Override
    public void onCreate() {
        super.onCreate();
        task = new TimerTask() {
            @Override
            public void run() {
                writeFile(logFile, "----hello----");
            }
        };
        timer = new Timer();
        timer.schedule(task, 10 * 1000, 10 * 1000);
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
}
