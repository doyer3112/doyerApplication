package doyer.wifiscaner;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import doyer.wifiscaner.gen.DaoMaster;
import doyer.wifiscaner.gen.DaoSession;

/**
 * Created by jie.du on 16/10/31.
 */
public class BaseApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "doyer");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
