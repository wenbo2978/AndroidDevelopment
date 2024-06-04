package cwb.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC_chen on 2019/3/23.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DB_NAME = "ip_db";
    public static final String TABLE_NAME = "ip_table";
    public static final String TABLE_NAME_HOT = "hot_table";
    public static final String KEY_IP = "ip";
    public static final String KEY_PORT = "port";
    public static final String HOT_KEY = "hot_key";
    public static final String HOT_VALUE = "hot_value";
    public static final int version = 3;


    public DataBaseHelper(Context context) {
        super(context,DB_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_tw = String.format("CREATE TABLE if not exists %s (_id INTEGER PRIMARY KEY AUTOINCREMENT,%s text,%s text)",TABLE_NAME,KEY_IP,KEY_PORT);
        String sql_tw2 = String.format("CREATE TABLE if not exists %s (_id INTEGER PRIMARY KEY AUTOINCREMENT,%s text,%s text)",TABLE_NAME_HOT,HOT_KEY,HOT_VALUE);
        db.execSQL(sql_tw2);
        db.execSQL(sql_tw);

    }

    public static ContentValues getContentValues(String ip,String port){
        ContentValues cv = new ContentValues();
        cv.put(KEY_IP,ip);
        cv.put(KEY_PORT,port);
        return cv;
    }
    public static ContentValues getContentValues_hot(String key,String value){
        ContentValues cv = new ContentValues();
        cv.put(HOT_KEY,key);
        cv.put(HOT_VALUE,value);
        return cv;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String sql = String.format("drop table if exists %s",TABLE_NAME);
            db.execSQL(sql);
            onCreate(db);

        }
}
