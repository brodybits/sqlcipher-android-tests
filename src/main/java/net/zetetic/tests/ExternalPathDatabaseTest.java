package net.zetetic.tests;


import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.zetetic.QueryHelper;
import net.zetetic.ZeteticApplication;

import java.io.File;
import java.util.Random;

public class ExternalPathDatabaseTest extends SQLCipherTest {
    private static final String STORAGE_DIRECTORY = "/ZeteticTestDB";
    /**
     *
     */
    private static String DB_PATH = genFilePath("/", true);
    @Override
    public boolean execute(SQLiteDatabase database) {
        database.close();
        ZeteticApplication.getInstance().deleteDatabaseFileAndSiblings(DB_PATH+ZeteticApplication.DATABASE_NAME);
        String databasesFolderPath = ZeteticApplication.getInstance().getDatabasePath(DB_PATH+ZeteticApplication.DATABASE_NAME).getParent();
        File databasesFolder = new File(databasesFolderPath);
        databasesFolder.delete();

        MyHelper databaseHelper = new MyHelper(ZeteticApplication.getInstance());

        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase(ZeteticApplication.DATABASE_PASSWORD);
        writableDatabase.beginTransaction();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase(ZeteticApplication.DATABASE_PASSWORD);

        Cursor results = readableDatabase.rawQuery("select count(*) from t1", new String[]{});
        results.moveToFirst();
        int rowCount = results.getInt(0);

        results.close();
        writableDatabase.endTransaction();
        readableDatabase.close();
        writableDatabase.close();

        return rowCount == 1;
    }



    public static String genFilePath(String prefix, boolean isCreate) {
        StringBuilder sb = new StringBuilder(Environment.getExternalStorageDirectory().toString());
        sb.append(STORAGE_DIRECTORY).append(prefix);
        File dir = new File(sb.toString());
        if (isCreate && !dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(ExternalPathDatabaseTest.class.getSimpleName(), "mkdirs fail:" + sb.toString());
            }
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return "External Path DatabaseTest";
    }

    private class MyHelper extends SQLiteOpenHelper {

        public MyHelper(Context context) {
            super(context, DB_PATH + ZeteticApplication.DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL("create table t1(a,b)");
            database.execSQL("insert into t1(a,b) values(?, ?)", new Object[]{"one for the money",
                    "two for the show"});
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
    }
}
