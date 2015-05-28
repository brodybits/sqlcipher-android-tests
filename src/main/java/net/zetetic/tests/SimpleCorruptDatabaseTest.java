package net.zetetic.tests;

import android.util.Log;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.zetetic.ZeteticApplication;

import java.io.File;
import java.io.IOException;

public class SimpleCorruptDatabaseTest extends SQLCipherTest {

    @Override
    public TestResult run() {

        TestResult result = new TestResult(getName(), false);
        try {
            result.setResult(execute(null));
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            Log.v(ZeteticApplication.TAG, e.toString());
        }
        return result;
    }

    @Override
    public boolean execute(SQLiteDatabase null_database_ignored) {

        File unencryptedDatabase = ZeteticApplication.getInstance().getDatabasePath("unencrypted-corrupt.db");

        try {
            ZeteticApplication.getInstance().extractAssetToDatabaseDirectory("unencrypted-corrupt.db");

            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(unencryptedDatabase, "", null);

            // NOTE: database not expected to be null, but check:
            if (database != null)
                database.close();

            return (database != null);

        } catch (Exception e) {
            // Uncaught exception (not expected):
            return false;
        }
        finally {
            unencryptedDatabase.delete();
        }
    }

    @Override
    public String getName() {
        return "Simple Corrupt Database Test";
    }
}
