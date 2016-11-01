package doyer.wifiscaner.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import doyer.wifiscaner.bean.WifiInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WIFI_INFO".
*/
public class WifiInfoDao extends AbstractDao<WifiInfo, Long> {

    public static final String TABLENAME = "WIFI_INFO";

    /**
     * Properties of entity WifiInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Ssid = new Property(1, String.class, "ssid", false, "SSID");
        public final static Property Bssid = new Property(2, String.class, "bssid", false, "BSSID");
        public final static Property Capabilities = new Property(3, String.class, "capabilities", false, "CAPABILITIES");
    }


    public WifiInfoDao(DaoConfig config) {
        super(config);
    }
    
    public WifiInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WIFI_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "\"SSID\" TEXT," + // 1: ssid
                "\"BSSID\" TEXT," + // 2: bssid
                "\"CAPABILITIES\" TEXT);"); // 3: capabilities
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WIFI_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WifiInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ssid = entity.getSsid();
        if (ssid != null) {
            stmt.bindString(2, ssid);
        }
 
        String bssid = entity.getBssid();
        if (bssid != null) {
            stmt.bindString(3, bssid);
        }
 
        String capabilities = entity.getCapabilities();
        if (capabilities != null) {
            stmt.bindString(4, capabilities);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WifiInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ssid = entity.getSsid();
        if (ssid != null) {
            stmt.bindString(2, ssid);
        }
 
        String bssid = entity.getBssid();
        if (bssid != null) {
            stmt.bindString(3, bssid);
        }
 
        String capabilities = entity.getCapabilities();
        if (capabilities != null) {
            stmt.bindString(4, capabilities);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public WifiInfo readEntity(Cursor cursor, int offset) {
        WifiInfo entity = new WifiInfo( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ssid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bssid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // capabilities
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WifiInfo entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setSsid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBssid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCapabilities(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WifiInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WifiInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WifiInfo entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
